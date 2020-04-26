package com.kurisani.superhero.module

interface BaseView {

    fun init()

    fun showProgress(show: Boolean)

    fun onErrorReceived(code: String, message: String) {
        // DO NOT REMOVE! Default implementation so that this does not have to be implemented by child views
    }
}