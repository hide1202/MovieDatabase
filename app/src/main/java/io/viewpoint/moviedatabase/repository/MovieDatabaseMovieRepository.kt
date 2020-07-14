package io.viewpoint.moviedatabase.repository

import arrow.core.getOrElse
import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.domain.repository.MovieRepository
import io.viewpoint.moviedatabase.model.api.Movie
import javax.inject.Inject

class MovieDatabaseMovieRepository @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {
    override suspend fun getPopular(): List<Movie> = movieApi.getPopular()
        .attempt()
        .suspended()
        .map {
            it.results
        }
        .getOrElse { emptyList() }

    override suspend fun getNowPlayings(): List<Movie> = movieApi.getNowPlaying()
        .attempt()
        .suspended()
        .map {
            it.results
        }
        .getOrElse { emptyList() }
}