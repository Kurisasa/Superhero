package com.kurisani.superhero.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SuccessResponse(
    val success: String = ""
): ApiBaseSuccessResponse