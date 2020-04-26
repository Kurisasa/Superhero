package com.kurisani.superhero.manager

import com.kurisani.superhero.SuperheroApplication
import com.kurisani.superhero.manager.interfaces.SuperheroManager
import com.kurisani.superhero.model.Results
import com.kurisani.superhero.repository.api.ApiUseCaseFactory
import io.reactivex.Single
import javax.inject.Inject

class SuperheroManagerImpl @Inject constructor() : BaseManagerImpl(), SuperheroManager {

    init {
        SuperheroApplication.instance?.getDependencyComponent()?.inject(this)
    }

    override fun getSuperHero(name: String): Single<Results> {
        return ApiUseCaseFactory.getSuperHeroApiUseCase()
            .perform(name)
            .map {
                it
            }
    }


}