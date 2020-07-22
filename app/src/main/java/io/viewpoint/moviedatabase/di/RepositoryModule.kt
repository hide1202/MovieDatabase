package io.viewpoint.moviedatabase.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.viewpoint.moviedatabase.domain.repository.*
import io.viewpoint.moviedatabase.platform.external.AppDatabase

@Module(includes = [RepositoryModule.ProvideRepositoryModule::class])
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

    @Module
    @InstallIn(ApplicationComponent::class)
    class ProvideRepositoryModule {
        @Provides
        fun wantToSeeRepository(database: AppDatabase): WantToSeeRepository =
            MovieDatabaseWantToSeeRepository(database.wantToSeeDao())
    }
}