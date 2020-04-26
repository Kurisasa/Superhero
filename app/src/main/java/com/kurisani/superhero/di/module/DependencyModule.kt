package com.kurisani.superhero.di.module

import com.kurisani.superhero.manager.SuperheroManagerImpl
import com.kurisani.superhero.manager.interfaces.SuperheroManager
import com.kurisani.superhero.module.home.HomeContract
import com.kurisani.superhero.module.home.HomeModelImpl
import com.kurisani.superhero.module.home.HomePresenterImpl
import com.kurisani.superhero.room.dao.SuperheroDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DependencyModule {

    @Provides
    internal fun superheroManager(): SuperheroManager {
        return SuperheroManagerImpl()
    }

    @Provides
    @Singleton
    internal fun homeModel(superheroManager: SuperheroManager, superheroDao: SuperheroDao): HomeContract.HomeModel {
        return HomeModelImpl(superheroManager, superheroDao)
    }

    @Provides
    @Singleton
    internal fun homePresenter(homeModel: HomeContract.HomeModel): HomeContract.HomePresenter {
        return HomePresenterImpl(homeModel)
    }
}
