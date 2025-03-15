package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.core.common.coroutines.suspendRunCatching
import io.viewpoint.moviedatabase.api.dto.MovieDto
import javax.inject.Inject

class MovieDatabaseMovieRepository @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {
    override suspend fun getPopular(page: Int): List<MovieDto> =
        suspendRunCatching {
            movieApi.getPopular(page)
        }.map {
            it.results
        }.getOrElse { emptyList() }

    override suspend fun getNowPlayings(page: Int): List<MovieDto> =
        suspendRunCatching {
            movieApi.getNowPlaying(page)
        }.map {
            it.results
        }.getOrElse { emptyList() }

    override suspend fun getUpcoming(page: Int): List<MovieDto> =
        suspendRunCatching {
            movieApi.getUpcoming(page)
        }.map {
            it.results
        }.getOrElse { emptyList() }

    override suspend fun getTopRated(page: Int): List<MovieDto> =
        suspendRunCatching {
            movieApi.getTopRated(page)
        }.map {
            it.results
        }.getOrElse { emptyList() }
}