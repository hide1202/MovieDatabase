package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.model.api.Movie

interface MovieRepository {
    suspend fun getPopular(): List<Movie>

    suspend fun getNowPlayings(): List<Movie>

    suspend fun getUpcoming(): List<Movie>

    suspend fun getTopRated(): List<Movie>
}