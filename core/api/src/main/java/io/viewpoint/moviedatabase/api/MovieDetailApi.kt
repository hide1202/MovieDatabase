package io.viewpoint.moviedatabase.api

import io.viewpoint.moviedatabase.model.api.CreditsResponse
import io.viewpoint.moviedatabase.model.api.KeywordResponse
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.model.api.MovieListResponse
import io.viewpoint.moviedatabase.model.api.WatchProviderResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailApi {
    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Int): MovieDetail

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(@Path("id") id: Int): CreditsResponse

    @GET("movie/{id}/keywords")
    suspend fun getKeywords(@Path("id") id: Int): KeywordResponse

    @GET("movie/{id}/recommendations")
    suspend fun getRecommendations(@Path("id") id: Int): MovieListResponse

    @GET("movie/{id}/watch/providers")
    suspend fun getWatchProviders(@Path("id") id: Int): WatchProviderResponse
}