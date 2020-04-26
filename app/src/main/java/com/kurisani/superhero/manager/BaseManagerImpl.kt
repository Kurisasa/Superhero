package com.kurisani.superhero.manager

import android.util.Log
import com.kurisani.superhero.manager.interfaces.BaseManager
import com.kurisani.superhero.model.ErrorResponse
import com.kurisani.superhero.repository.RepositoriesFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

abstract class BaseManagerImpl: BaseManager {

    private val compositeDisposable = CompositeDisposable()

    protected fun registerDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    protected fun createErrorResponse(error: Throwable): ErrorResponse {
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