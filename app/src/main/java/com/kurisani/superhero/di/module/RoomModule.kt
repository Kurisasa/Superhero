package com.kurisani.superhero.di.module

import com.kurisani.superhero.SuperheroApplication
import com.kurisani.superhero.room.dao.SuperheroDao
import com.kurisani.superhero.room.database.SuperheroDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: SuperheroApplication) {

    private val database: SuperheroDatabase = SuperheroDatabase.getAppDataBase(context = application)!!

    @Singleton
    @Provides
    fun providesSuperheroDatabase(): SuperheroDatabase {
        return database
    }

    @Singleton
    @Provides
    fun providesSuperheroDao(database: SuperheroDatabase): SuperheroDao {
        return database.superheroDao()
    }
}