package com.kurisani.superhero.module.home

import com.kurisani.superhero.model.Results
import com.kurisani.superhero.model.SuperHeroResponse
import com.kurisani.superhero.module.BasePresenter
import com.kurisani.superhero.module.BaseView
import com.kurisani.superhero.room.entity.Superhero
import io.reactivex.Single

interface HomeContract {

    interface HomeModel {
        fun fetchHero(heroName: String): Single<Results>

        fun saveSuperhero(heroId: Int, name: String,intelligence: Int, strength: Int,speed: Int,durability: Int,power: Int,
                          combat: Int,gender: String,race: String,height: String, weight:String,eyeColor: String,
                          hairColor: String,fullName: String,alterEgos: String, aliases: String,placeOfBirth: String,
                          firstAppearance: String,publisher: String,alignment: String, groupAffiliation: String,relatives: String,
                          imageUrl: String, occupation: String, base: String)

        fun getDatabaseList(): List<Superhero>?
    }

    interface HomeView : BaseView {
        fun onFetchHeroSuccess(response:Results)

        fun onFetchOfflineHeroSuccess(response: List<Superhero>?)

        fun showDetails(hero: SuperHeroResponse)

        fun showOfflineDetails(hero: Superhero)

        fun showErrors()
    }

    interface HomePresenter : BasePresenter<HomeView> {
        fun fetchHero(heroName: String)

        fun getLocalSuperHeroes(): List<Superhero>?
    }
}
