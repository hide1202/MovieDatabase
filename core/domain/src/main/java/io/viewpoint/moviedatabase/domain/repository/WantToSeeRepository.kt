package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.api.dto.MovieDetailDto

interface WantToSeeRepository {
    suspend fun hasWantToSeeMovie(id: Int): Boolean

    suspend fun getWantToSeeMovies(): List<MovieDetailDto>

    suspend fun addWantToSeeMovie(id: Int)

    suspend fun removeWantToSeeMovie(id: Int)
}