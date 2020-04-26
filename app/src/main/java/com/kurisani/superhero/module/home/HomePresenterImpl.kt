package com.kurisani.superhero.module.home

import android.annotation.SuppressLint
import androidx.room.EmptyResultSetException
import com.kurisani.superhero.model.Results
import com.kurisani.superhero.presenter.BasePresenterImpl
import com.kurisani.superhero.room.entity.Superhero
import com.kurisani.superhero.util.Util
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenterImpl @Inject constructor(
    private val homeModel: HomeContract.HomeModel)
    : BasePresenterImpl(), HomeContract.HomePresenter {

    private lateinit var homeView: HomeContract.HomeView

    override fun attachView(view: HomeContract.HomeView) {
        this.homeView = view
    }

    @SuppressLint("CheckResult")
    override fun fetchHero(heroName: String) {
        if (Util.isOnline()) {
            homeModel.fetchHero(heroName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {homeView.showProgress(true) }
                .doFinally {homeView.showProgress(false) }
                .subscribe(
                    { response -> homeView.onFetchHeroSuccess(response)
                        saveToDB(response)
                   },
                    { homeView.showErrors() }
                )
        }
    }

    private fun saveToDB(response: Results) {
        homeModel.saveSuperhero(response.results[0].id, response.results[0].name,
            response.results[0].powerstats?.intelligence!!,response.results[0].powerstats?.strength!!,
            response.results[0].powerstats?.speed!!,response.results[0].powerstats?.durability!!,
            response.results[0].powerstats?.power!!, response.results[0].powerstats?.combat!!,
            response.results[0].appearance?.gender!!,
            response.results[0].appearance?.race!!, response.results[0].appearance?.height.toString(),
            response.results[0].appearance?.weight.toString(), response.results[0].appearance?.eyeColor!!,
            response.results[0].appearance?.hairColor!!,response.results[0].biography?.fullName!!,
            response.results[0].biography?.alterEgos!!,response.results[0].biography?.aliases.toString(),
            response.results[0].biography?.placeOfBirth!!,response.results[0].biography?.firstAppearance!!,
            response.results[0].biography?.publisher!!,response.results[0].biography?.alignment!!,
            response.results[0].connections?.groupAffiliation!!,response.results[0].connections?.relatives!!,
            response.results[0].image?.url!!, response.results[0].work?.occupation!!,  response.results[0].work?.base!!
        )
    }

    @SuppressLint("CheckResult")
    override fun getLocalSuperHeroes(): List<Superhero>? {
        var offlineHeroes: List<Superhero>? = null
        Observable.fromCallable {
            homeModel.getDatabaseList()
        }.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                offlineHeroes = it
                homeView.onFetchOfflineHeroSuccess(it)
            }, {
                if (it is EmptyResultSetException) {
                    it.printStackTrace()
                } else {
                    it.printStackTrace()
                }
            })
        return offlineHeroes
    }

    override fun onDestroy() {

    }
}
