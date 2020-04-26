package com.kurisani.superhero.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Work(
    val occupation: String,
    val base: String
): Serializable