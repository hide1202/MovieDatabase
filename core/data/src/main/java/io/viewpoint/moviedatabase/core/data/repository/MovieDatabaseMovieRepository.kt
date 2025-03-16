package io.viewpoint.moviedatabase.core.data.repository

import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.core.common.coroutines.suspendRunCatching
import io.viewpoint.moviedatabase.domain.model.Movie
import io.viewpoint.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

class MovieDatabaseMovieRepository @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {
    override suspend fun getPopular(page: Int): List<Movie> =
        suspendRunCatching {
            movieApi.getPopular(page)
        }.map {
            it.results.map { it.asDomain() }
        }.getOrElse { emptyList() }

    override suspend fun getNowPlayings(page: Int): List<Movie> =
        suspendRunCatching {
            movieApi.getNowPlaying(page)
        }.map {
            it.results.map { it.asDomain() }
        }.getOrElse { emptyList() }

    override suspend fun getUpcoming(page: Int): List<Movie> =
        suspendRunCatching {
            movieApi.getUpcoming(page)
        }.map {
            it.results.map { it.asDomain() }
        }.getOrElse { emptyList() }

    override suspend fun getTopRated(page: Int): List<Movie> =
        suspendRunCatching {
            movieApi.getTopRated(page)
        }.map {
            it.results.map { it.asDomain() }
        }.getOrElse { emptyList() }
}