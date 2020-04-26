package com.kurisani.superhero.repository

import com.kurisani.superhero.SuperheroApplication
import com.kurisani.superhero.repository.api.ApiRepository
import com.kurisani.superhero.rx.SuperheroRxSchedulers
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object RepositoriesFactory {

    val context = SuperheroApplication.instance!!
    // LOOKOUT! DON'T TOUCH ORDER OF ADAPTERS, Polymorphic adapters should be firstQ
    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()
    val rxSchedulers = SuperheroRxSchedulers()
    val apiRepository = ApiRepository(moshi, rxSchedulers)
}