package com.kurisani.superhero.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Connections(
    @Json(name = "group-affiliation")
    val groupAffiliation: String,
    val relatives: String
): Serializable