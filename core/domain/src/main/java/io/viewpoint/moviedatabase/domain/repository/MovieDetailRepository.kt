package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.api.dto.CreditDto
import io.viewpoint.moviedatabase.api.dto.KeywordDto
import io.viewpoint.moviedatabase.api.dto.MovieDto
import io.viewpoint.moviedatabase.api.dto.MovieDetailDto
import io.viewpoint.moviedatabase.api.dto.WatchProviderDto

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: Int): MovieDetailDto?

    suspend fun getCredits(movieId: Int): List<CreditDto>

    suspend fun getKeywords(movieId: Int): List<KeywordDto>

    suspend fun getRecommendations(movieId: Int): List<MovieDto>

    suspend fun getWatchProviders(movieId: Int, countryCode: String): WatchProviderDto?
}