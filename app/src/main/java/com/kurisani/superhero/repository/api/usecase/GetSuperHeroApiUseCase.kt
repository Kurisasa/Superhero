package com.kurisani.superhero.repository.api.usecase

import com.kurisani.superhero.model.Results
import com.kurisani.superhero.repository.api.ApiRepository
import com.kurisani.superhero.rx.RxSchedulers
import io.reactivex.Single

class GetSuperHeroApiUseCase(
    private val apiRepository: ApiRepository,
    private val rxSchedulers: RxSchedulers
) {

    fun perform(name: String): Single<Results> =
        apiRepository.api.getSuperhero(name)
            .observeOn(rxSchedulers.observeOn())
            .doOnError(apiRepository::checkIsFatalUserAuthError)
            .map { it }

}