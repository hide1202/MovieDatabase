package io.viewpoint.moviedatabase.api

import arrow.fx.IO
import io.viewpoint.moviedatabase.model.api.CreditsResponse
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.model.api.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id: Int): IO<MovieDetail>

    @GET("movie/popular")
    fun getPopular(): IO<MovieListResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(): IO<MovieListResponse>

    @GET("movie/upcoming")
    fun getUpcoming(): IO<MovieListResponse>

    @GET("movie/top_rated")
    fun getTopRated(): IO<MovieListResponse>

    @GET("movie/{id}/credits")
    fun getMovieCredits(@Path("id") id: Int): IO<CreditsResponse>
}