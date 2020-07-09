package io.viewpoint.moviedatabase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieRepository
import io.viewpoint.moviedatabase.domain.repository.SearchRepository
import io.viewpoint.moviedatabase.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.repository.MovieDatabaseMovieRepository
import io.viewpoint.moviedatabase.repository.MovieDatabaseSearchRepository

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun configurationRepository(
        repository: MovieDatabaseConfigurationRepository
    ): ConfigurationRepository

    @Binds
    abstract fun movieRepository(
        repository: MovieDatabaseMovieRepository
    ): MovieRepository

    @Binds
    abstract fun searchRepository(
        repository: MovieDatabaseSearchRepository
    ): SearchRepository
}