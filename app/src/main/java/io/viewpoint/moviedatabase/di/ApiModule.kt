package io.viewpoint.moviedatabase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.viewpoint.moviedatabase.BuildConfig
import io.viewpoint.moviedatabase.api.ConfigurationApi
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.api.SearchApi
import timber.log.Timber

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {
    @Provides
    fun movieDatabaseApi(): MovieDatabaseApi = MovieDatabaseApi
        .Builder()
        .apply {
            apiKey = BuildConfig.API_KEY
            if (BuildConfig.DEBUG) {
                debugLog = {
                    Timber.d(it)
                }
            }
        }
        .build()

    @Provides
    fun configurationApi(movieDatabaseApi: MovieDatabaseApi): ConfigurationApi =
        movieDatabaseApi.get()

    @Provides
    fun movieApi(movieDatabaseApi: MovieDatabaseApi): MovieApi =
        movieDatabaseApi.get()

    @Provides
    fun searchApi(movieDatabaseApi: MovieDatabaseApi): SearchApi =
        movieDatabaseApi.get()
}