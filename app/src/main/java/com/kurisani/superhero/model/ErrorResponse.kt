package com.kurisani.superhero.model

import com.kurisani.superhero.util.constants.ApiErrorConstants
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val errorCode: String = ApiErrorConstants.API_ERROR_FORBIDDEN,
    val errorMessage: String = ""
) : ApiBaseErrorResponse
