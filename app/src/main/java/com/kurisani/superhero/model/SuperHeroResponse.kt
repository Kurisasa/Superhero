package com.kurisani.superhero.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class SuperHeroResponse(
    val id: Int = 0,
    val name: String = "",
    var powerstats: Powerstats? = null,
    var biography: Biography? = null,
    var appearance: Appearance? = null,
    var work: Work? = null,
    var connections: Connections? = null,
    var image: Image? = null
): ApiBaseSuccessResponse, Serializable