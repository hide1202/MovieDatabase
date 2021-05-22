package io.viewpoint.moviedatabase.domain.repository

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.getOrElse
import io.viewpoint.moviedatabase.api.MovieDetailApi
import io.viewpoint.moviedatabase.model.api.*
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

    override suspend fun getRecommendations(movieId: Int): List<Movie> =
        movieDetailApi.getRecommendations(movieId)
            .attempt()
            .suspended()
            .map {
                it.results
            }
            .getOrElse { emptyList() }

    override suspend fun getWatchProviders(
        movieId: Int,
        countryCode: String
    ): WatchProvider? =
        movieDetailApi.getWatchProviders(movieId)
            .attempt()
            .suspended()
            .flatMap {
                val watchProvider = it.results[countryCode]
                if (watchProvider != null) {
                    Either.Right(watchProvider)
                } else {
                    Either.Left(Unit)
                }
            }
            .getOrElse { null }
}