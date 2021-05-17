package io.viewpoint.moviedatabase.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.viewpoint.moviedatabase.api.MovieDetailApi
import io.viewpoint.moviedatabase.domain.repository.*
import io.viewpoint.moviedatabase.platform.external.AppDatabase
import javax.inject.Singleton

@Module(includes = [RepositoryModule.ProvideRepositoryModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun configurationRepository(
        repository: MovieDatabaseConfigurationRepository
    ): ConfigurationRepository

    @Binds
    @Singleton
    abstract fun movieRepository(
        repository: MovieDatabaseMovieRepository
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun movieDetailRepository(
        repository: MovieDatabaseMovieDetailRepository
    ): MovieDetailRepository

    @Binds
    @Singleton
    abstract fun searchRepository(
        repository: MovieDatabaseSearchRepository
    ): SearchRepository

    @Module
    @InstallIn(SingletonComponent::class)
    class ProvideRepositoryModule {
        @Provides
        @Singleton
        fun wantToSeeRepository(
            movieDetailApi: MovieDetailApi,
            database: AppDatabase
        ): WantToSeeRepository =
            MovieDatabaseWantToSeeRepository(movieDetailApi, database.wantToSeeDao())
    }
}