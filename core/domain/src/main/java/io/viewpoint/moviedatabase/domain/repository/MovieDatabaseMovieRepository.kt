package io.viewpoint.moviedatabase.domain.repository

import arrow.core.getOrElse
import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.model.api.Movie
import javax.inject.Inject

class MovieDatabaseMovieRepository @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {
    override suspend fun getPopular(page: Int): List<Movie> = movieApi.getPopular(page)
        .attempt()
        .suspended()
        .map {
            it.results
        }
        .getOrElse { emptyList() }

    override suspend fun getNowPlayings(page: Int): List<Movie> = movieApi.getNowPlaying(page)
        .attempt()
        .suspended()
        .map {
            it.results
        }
        .getOrElse { emptyList() }

    override suspend fun getUpcoming(page: Int): List<Movie> = movieApi.getUpcoming(page)
        .attempt()
        .suspended()
        .map {
            it.results
        }
        .getOrElse { emptyList() }

    override suspend fun getTopRated(page: Int): List<Movie> = movieApi.getTopRated(page)
        .attempt()
        .suspended()
        .map {
            it.results
        }
        .getOrElse { emptyList() }
}