package com.kurisani.superhero.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Results(
    val response: String = "",
    val resultsFor: String = "",
    val results: List<SuperHeroResponse>
): ApiBaseSuccessResponse