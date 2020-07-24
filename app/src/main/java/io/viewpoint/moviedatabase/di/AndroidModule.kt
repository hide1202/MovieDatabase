package io.viewpoint.moviedatabase.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.platform.common.AndroidPreferencesService
import io.viewpoint.moviedatabase.platform.external.AppDatabase

@Module
@InstallIn(ApplicationComponent::class)
class AndroidModule {
    @Provides
    fun preferencesService(@ApplicationContext context: Context): PreferencesService =
        AndroidPreferencesService(context)

    @Provides
    fun applicationDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "movie-database"
        ).build()
}
