package io.viewpoint.moviedatabase.mock

import arrow.core.getOrElse
import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.domain.repository.MovieRepository
import io.viewpoint.moviedatabase.model.api.Movie
import io.viewpoint.moviedatabase.model.api.MovieDetail

class TestMovieRepository(
    private val movieApi: MovieApi
) : MovieRepository {
    override suspend fun getMovieDetail(movieId: Int): MovieDetail? =
        movieApi.getMovieDetail(movieId)
            .attempt()
            .suspended()
            .orNull()

    override suspend fun getPopular(): List<Movie> =
        movieApi.getPopular()
            .attempt()
            .suspended()
            .map {
                it.results
            }
            .getOrElse { emptyList() }

    override suspend fun getNowPlayings(): List<Movie> =
        movieApi.getNowPlaying()
            .attempt()
            .suspended()
            .map {
                it.results
            }
            .getOrElse { emptyList() }

    override suspend fun getUpcoming(): List<Movie> =
        movieApi.getUpcoming()
            .attempt()
            .suspended()
            .map {
                it.results
            }
            .getOrElse { emptyList() }

    override suspend fun getTopRated(): List<Movie> =
        movieApi.getTopRated()
            .attempt()
            .suspended()
            .map {
                it.results
            }
            .getOrElse { emptyList() }
}