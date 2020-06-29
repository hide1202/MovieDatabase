package io.viewpoint.moviedatabase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.repository.MovieDatabaseConfigurationRepository

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun configurationRepository(
        repository: MovieDatabaseConfigurationRepository
    ): ConfigurationRepository
}