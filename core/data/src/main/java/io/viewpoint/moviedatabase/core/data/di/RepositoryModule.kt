package io.viewpoint.moviedatabase.core.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.viewpoint.moviedatabase.api.MovieDetailApi
import io.viewpoint.moviedatabase.core.data.database.AppDatabase
import io.viewpoint.moviedatabase.core.data.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.core.data.repository.MovieDatabaseMovieDetailRepository
import io.viewpoint.moviedatabase.core.data.repository.MovieDatabaseMovieRepository
import io.viewpoint.moviedatabase.core.data.repository.MovieDatabaseSearchRepository
import io.viewpoint.moviedatabase.core.data.repository.MovieDatabaseWantToSeeRepository
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDetailRepository
import io.viewpoint.moviedatabase.domain.repository.MovieRepository
import io.viewpoint.moviedatabase.domain.repository.SearchRepository
import io.viewpoint.moviedatabase.domain.repository.WantToSeeRepository
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