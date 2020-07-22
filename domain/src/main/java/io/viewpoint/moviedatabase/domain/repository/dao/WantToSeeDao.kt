package io.viewpoint.moviedatabase.domain.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.viewpoint.moviedatabase.domain.repository.entity.WantToSeeMovieEntity

@Dao
interface WantToSeeDao {
    @Query("SELECT * FROM ${WantToSeeMovieEntity.TABLE_NAME} WHERE id=(:id)")
    suspend fun getOne(id: Int): WantToSeeMovieEntity

    @Query("SELECT * FROM ${WantToSeeMovieEntity.TABLE_NAME}")
    suspend fun getAll(): List<WantToSeeMovieEntity>

    @Insert
    suspend fun insert(entity: WantToSeeMovieEntity)

    @Delete
    suspend fun delete(entity: WantToSeeMovieEntity)
}