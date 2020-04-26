package com.kurisani.superhero.di.module

import android.app.Application
import android.content.Context
import com.kurisani.superhero.rx.SuperheroRxSchedulers
import com.kurisani.superhero.rx.RxSchedulers
import com.kurisani.superhero.SuperheroApplication
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Module
class ApplicationModule(application: SuperheroApplication) {
    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        internal fun applicationContext(application: Application) = application.applicationContext as Context

        @Provides
        @JvmStatic
        fun providesRxSchedulers(): RxSchedulers {
            return SuperheroRxSchedulers()
        }
    }
}

@Singleton
@Component(modules = [
    ApplicationModule::class
])
interface ApplicationComponent : AndroidInjector<SuperheroApplication> {

    fun applicationContext(): Context

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}