package io.viewpoint.moviedatabase.domain.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.viewpoint.moviedatabase.domain.repository.entity.WantToSeeMovieEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class WantToSeeMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val poster_path: String?
) {
    companion object {
        const val TABLE_NAME = "WantToSeeMovie"
    }
}