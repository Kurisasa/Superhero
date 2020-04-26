package com.kurisani.superhero.repository.api

import com.kurisani.superhero.model.Results
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiMethods {
    @GET("search/{name}")
    fun getSuperhero(@Path("name") name: String): Single<Results>
}