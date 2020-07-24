package io.viewpoint.moviedatabase.platform.external

import androidx.room.Database
import androidx.room.RoomDatabase
import io.viewpoint.moviedatabase.BuildConfig
import io.viewpoint.moviedatabase.domain.repository.dao.WantToSeeDao
import io.viewpoint.moviedatabase.domain.repository.entity.WantToSeeMovieEntity

@Database(entities = [WantToSeeMovieEntity::class], version = BuildConfig.DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wantToSeeDao(): WantToSeeDao
}