package com.kurisani.superhero.presenter

import com.kurisani.superhero.module.BaseView
import com.kurisani.superhero.util.ErrorUtil


abstract class BasePresenterImpl {

    protected fun handleError(throwable: Throwable, view: BaseView) {
        val errorResponse = ErrorUtil.createErrorResponse(throwable)
        view.onErrorReceived(
            errorResponse.errorCode,
            errorResponse.errorMessage
        )
    }

}