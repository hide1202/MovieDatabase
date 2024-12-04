package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.api.MovieDetailApi
import io.viewpoint.moviedatabase.core.common.coroutines.suspendRunCatching
import io.viewpoint.moviedatabase.model.api.Credit
import io.viewpoint.moviedatabase.model.api.Keyword
import io.viewpoint.moviedatabase.model.api.Movie
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.model.api.WatchProvider
import javax.inject.Inject

class MovieDatabaseMovieDetailRepository @Inject constructor(
    private val movieDetailApi: MovieDetailApi
) : MovieDetailRepository {
    override suspend fun getMovieDetail(movieId: Int): MovieDetail? =
        suspendRunCatching {
            movieDetailApi.getMovieDetail(movieId)
        }.getOrNull()

    override suspend fun getCredits(movieId: Int): List<Credit> =
        suspendRunCatching {
            movieDetailApi.getMovieCredits(movieId)
        }.map {
            it.cast + it.crew
        }.getOrElse { emptyList() }

    override suspend fun getKeywords(movieId: Int): List<Keyword> =
        suspendRunCatching {
            movieDetailApi.getKeywords(movieId)
        }.map {
            it.keywords
        }.getOrElse { emptyList() }

    override suspend fun getRecommendations(movieId: Int): List<Movie> =
        suspendRunCatching {
            movieDetailApi.getRecommendations(movieId)
        }.map {
            it.results
        }.getOrElse { emptyList() }

    override suspend fun getWatchProviders(
        movieId: Int,
        countryCode: String
    ): WatchProvider? =
        suspendRunCatching {
            movieDetailApi.getWatchProviders(movieId)
        }.mapCatching {
            val watchProvider = it.results[countryCode]
            if (watchProvider != null) {
                watchProvider
            } else {
                null
            }
        }.getOrNull()
}