package com.kurisani.superhero.repository.api

import com.kurisani.superhero.repository.RepositoriesFactory
import com.kurisani.superhero.repository.RepositoriesFactory.rxSchedulers
import com.kurisani.superhero.repository.api.usecase.GetSuperHeroApiUseCase

object ApiUseCaseFactory {

    private val apiRepository = RepositoriesFactory.apiRepository

    fun getSuperHeroApiUseCase() = GetSuperHeroApiUseCase(apiRepository, rxSchedulers)
}