package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.model.api.MovieDetail

interface WantToSeeRepository {
    suspend fun hasWantToSeeMovie(id: Int): Boolean

    suspend fun getWantToSeeMovies(): List<MovieDetail>

    suspend fun addWantToSeeMovie(id: Int)

    suspend fun removeWantToSeeMovie(id: Int)
}