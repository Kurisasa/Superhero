package com.kurisani.superhero.manager.interfaces

import com.kurisani.superhero.model.Results
import io.reactivex.Single

interface SuperheroManager: BaseManager {

    fun getSuperHero(name: String): Single<Results>
}