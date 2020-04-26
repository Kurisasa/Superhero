package com.kurisani.superhero

import android.annotation.SuppressLint
import android.app.Application
import com.kurisani.superhero.di.module.ApplicationModule
import com.kurisani.superhero.di.component.DependencyComponent
import com.kurisani.superhero.di.module.DependencyModule
import com.kurisani.superhero.di.module.RoomModule
import es.dmoral.toasty.Toasty

class SuperheroApplication : Application() {

    private var dependencyComponent: DependencyComponent? = null

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        instance = this

        dependencyComponent = com.kurisani.superhero.di.component.DaggerDependencyComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .dependencyModule(DependencyModule())
            .roomModule(RoomModule(this))
            .build()

        initSDKs()
        initCallbacks()
    }

    private fun initSDKs(){
        Toasty.Config.getInstance()
            .tintIcon(true)
            .allowQueue(true)
            .apply()
    }

    private fun initCallbacks() {
        val myLifeCycle = SuperheroLifecycleCallback()
        registerActivityLifecycleCallbacks(myLifeCycle)
        registerComponentCallbacks(myLifeCycle)
    }

    fun getDependencyComponent(): DependencyComponent {
        return instance?.dependencyComponent!!
    }

    companion object {
        const val IMAGES_FILE_PROVIDER_PATH = "Superhero/"
        var instance: SuperheroApplication? = null
            private set
    }
}