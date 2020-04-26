package com.kurisani.superhero.util

import android.util.Log
import com.kurisani.superhero.model.ErrorResponse
import com.kurisani.superhero.repository.RepositoriesFactory
import retrofit2.HttpException

class ErrorUtil {

    companion object {
        fun createErrorResponse(error: Throwable): ErrorResponse {
            if (error is HttpException) {
                // Kotlin will smart cast at this point
                val errorJsonString = error.response()?.errorBody()?.string()
                val jsonAdapter = RepositoriesFactory.moshi.adapter(ErrorResponse::class.java)
                val errorResponse = jsonAdapter.fromJson(errorJsonString!!)

                return errorResponse!!
            } else {
                Log.e("Error", error.message)
            }

            return ErrorResponse("", "")
        }
    }
}