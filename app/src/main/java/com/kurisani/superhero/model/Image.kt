package com.kurisani.superhero.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Image(
    val url: String
): Serializable