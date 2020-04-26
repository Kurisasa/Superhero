package com.kurisani.superhero.repository

import com.kurisani.superhero.repository.api.ApiRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun providesMoshi(): Moshi = RepositoriesFactory.moshi

        @Provides
        @JvmStatic
        @Singleton
        internal fun providesApiRepository(): ApiRepository {
            return RepositoriesFactory.apiRepository
        }
    }
}