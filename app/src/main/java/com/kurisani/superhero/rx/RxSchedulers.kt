package com.kurisani.superhero.rx

import io.reactivex.Scheduler

interface RxSchedulers {
    fun subscribeOn(): Scheduler

    fun observeOn(): Scheduler
}