package com.kurisani.superhero.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Appearance(
    val gender: String,
    val race: String,
    val height: List<String>,
    val weight: List<String>,
    @Json(name = "eye-color")
    val eyeColor: String,
    @Json(name = "hair-color")
    val hairColor: String
): Serializable