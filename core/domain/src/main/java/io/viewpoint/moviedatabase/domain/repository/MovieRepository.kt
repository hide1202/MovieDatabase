package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.api.dto.MovieDto

interface MovieRepository {
    suspend fun getPopular(page: Int = 1): List<MovieDto>

    suspend fun getNowPlayings(page: Int = 1): List<MovieDto>

    suspend fun getUpcoming(page: Int = 1): List<MovieDto>

    suspend fun getTopRated(page: Int = 1): List<MovieDto>
}