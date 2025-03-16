package io.viewpoint.moviedatabase.core.data.repository

import io.viewpoint.moviedatabase.api.MovieDetailApi
import io.viewpoint.moviedatabase.core.common.coroutines.suspendRunCatching
import io.viewpoint.moviedatabase.api.dto.CreditDto
import io.viewpoint.moviedatabase.api.dto.KeywordDto
import io.viewpoint.moviedatabase.api.dto.MovieDto
import io.viewpoint.moviedatabase.api.dto.MovieDetailDto
import io.viewpoint.moviedatabase.api.dto.WatchProviderDto
import io.viewpoint.moviedatabase.domain.repository.MovieDetailRepository
import javax.inject.Inject

class MovieDatabaseMovieDetailRepository @Inject constructor(
    private val movieDetailApi: MovieDetailApi
) : MovieDetailRepository {
    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto? =
        suspendRunCatching {
            movieDetailApi.getMovieDetail(movieId)
        }.getOrNull()

    override suspend fun getCredits(movieId: Int): List<CreditDto> =
        suspendRunCatching {
            movieDetailApi.getMovieCredits(movieId)
        }.map {
            it.cast + it.crew
        }.getOrElse { emptyList() }

    override suspend fun getKeywords(movieId: Int): List<KeywordDto> =
        suspendRunCatching {
            movieDetailApi.getKeywords(movieId)
        }.map {
            it.keywords
        }.getOrElse { emptyList() }

    override suspend fun getRecommendations(movieId: Int): List<MovieDto> =
        suspendRunCatching {
            movieDetailApi.getRecommendations(movieId)
        }.map {
            it.results
        }.getOrElse { emptyList() }

    override suspend fun getWatchProviders(
        movieId: Int,
        countryCode: String
    ): WatchProviderDto? =
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