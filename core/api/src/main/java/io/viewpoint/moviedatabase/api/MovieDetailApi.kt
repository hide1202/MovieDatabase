package io.viewpoint.moviedatabase.api

import io.viewpoint.moviedatabase.api.dto.CreditsResponse
import io.viewpoint.moviedatabase.api.dto.KeywordResponse
import io.viewpoint.moviedatabase.api.dto.MovieDetailDto
import io.viewpoint.moviedatabase.api.dto.MovieListResponse
import io.viewpoint.moviedatabase.api.dto.WatchProviderResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailApi {
    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Int): MovieDetailDto

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(@Path("id") id: Int): CreditsResponse

    @GET("movie/{id}/keywords")
    suspend fun getKeywords(@Path("id") id: Int): KeywordResponse

    @GET("movie/{id}/recommendations")
    suspend fun getRecommendations(@Path("id") id: Int): MovieListResponse

    @GET("movie/{id}/watch/providers")
    suspend fun getWatchProviders(@Path("id") id: Int): WatchProviderResponse
}