package io.viewpoint.moviedatabase.domain.repository

import arrow.core.getOrElse
import io.viewpoint.moviedatabase.api.MovieDetailApi
import io.viewpoint.moviedatabase.model.api.Credit
import io.viewpoint.moviedatabase.model.api.Keyword
import io.viewpoint.moviedatabase.model.api.MovieDetail
import javax.inject.Inject

class MovieDatabaseMovieDetailRepository @Inject constructor(
    private val movieDetailApi: MovieDetailApi
) : MovieDetailRepository {
    override suspend fun getMovieDetail(movieId: Int): MovieDetail? =
        movieDetailApi.getMovieDetail(movieId)
            .attempt()
            .suspended()
            .getOrElse { null }

    override suspend fun getCredits(movieId: Int): List<Credit> =
        movieDetailApi.getMovieCredits(movieId)
            .attempt()
            .suspended()
            .map {
                it.cast + it.crew
            }
            .getOrElse { emptyList() }

    override suspend fun getKeywords(movieId: Int): List<Keyword> =
        movieDetailApi.getKeywords(movieId)
            .attempt()
            .suspended()
            .map {
                it.keywords
            }
            .getOrElse { emptyList() }
}