package io.viewpoint.moviedatabase.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.viewpoint.moviedatabase.api.*
import io.viewpoint.moviedatabase.core.data.BuildConfig
import io.viewpoint.moviedatabase.core.data.util.Flippers
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun movieDatabaseApi(): MovieDatabaseApi = MovieDatabaseApi
        .Builder()
        .apply {
//            apiKey = BuildConfig.API_KEY
            if (BuildConfig.DEBUG) {
                debugLog = {
                    Timber.d(it)
                }
            }

            Flippers.networkInterceptor()?.let { interceptor ->
                addInterceptor(MovieDatabaseApi.Builder.InterceptorType.NETWORK, interceptor)
            }
        }
        .build()

    @Provides
    @Singleton
    fun configurationApi(movieDatabaseApi: MovieDatabaseApi): ConfigurationApi =
        movieDatabaseApi.get()

    @Provides
    @Singleton
    fun movieApi(movieDatabaseApi: MovieDatabaseApi): MovieApi = movieDatabaseApi.get()

    @Provides
    @Singleton
    fun movieDetailApi(movieDatabaseApi: MovieDatabaseApi): MovieDetailApi = movieDatabaseApi.get()

    @Provides
    @Singleton
    fun searchApi(movieDatabaseApi: MovieDatabaseApi): SearchApi = movieDatabaseApi.get()
}