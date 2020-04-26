package com.kurisani.superhero.module

interface BasePresenter<T : BaseView> {

    fun attachView(view: T)

    fun onDestroy()
}