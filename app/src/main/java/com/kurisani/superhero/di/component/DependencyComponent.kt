package com.kurisani.superhero.di.component

import com.kurisani.superhero.activity.BaseActivity
import com.kurisani.superhero.di.module.ApplicationModule
import com.kurisani.superhero.di.module.DependencyModule
import com.kurisani.superhero.di.module.RoomModule
import com.kurisani.superhero.manager.interfaces.SuperheroManager
import com.kurisani.superhero.module.home.HomeFragment
import com.kurisani.superhero.module.home.HomeModelImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DependencyModule::class, RoomModule::class])
interface DependencyComponent {

    // Models
    fun inject(model: HomeModelImpl)

    // Managers
    fun inject(manager: SuperheroManager)

    // Activities
    fun inject(activity: BaseActivity)

    //fragment
    fun inject(fragment: HomeFragment)
}