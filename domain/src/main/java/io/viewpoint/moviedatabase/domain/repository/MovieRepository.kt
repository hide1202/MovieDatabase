package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.model.api.Movie

interface MovieRepository {
    suspend fun getPopular(page: Int = 1): List<Movie>

    suspend fun getNowPlayings(page: Int = 1): List<Movie>

    suspend fun getUpcoming(page: Int = 1): List<Movie>

    suspend fun getTopRated(page: Int = 1): List<Movie>
}