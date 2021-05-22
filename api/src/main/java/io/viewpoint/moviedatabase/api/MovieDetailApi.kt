package io.viewpoint.moviedatabase.api

import arrow.fx.IO
import io.viewpoint.moviedatabase.model.api.*
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailApi {
    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id: Int): IO<MovieDetail>

    @GET("movie/{id}/credits")
    fun getMovieCredits(@Path("id") id: Int): IO<CreditsResponse>

    @GET("movie/{id}/keywords")
    fun getKeywords(@Path("id") id: Int): IO<KeywordResponse>

    @GET("movie/{id}/recommendations")
    fun getRecommendations(@Path("id") id: Int): IO<MovieListResponse>

    @GET("movie/{id}/watch/providers")
    fun getWatchProviders(@Path("id") id: Int): IO<WatchProviderResponse>
}