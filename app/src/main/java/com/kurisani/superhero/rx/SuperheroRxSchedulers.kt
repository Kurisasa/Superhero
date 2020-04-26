package com.kurisani.superhero.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SuperheroRxSchedulers : RxSchedulers {

    override fun subscribeOn(): Scheduler {
        return Schedulers.io()
    }

    override fun observeOn(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
