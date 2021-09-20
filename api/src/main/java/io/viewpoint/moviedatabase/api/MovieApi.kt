package io.viewpoint.moviedatabase.api

import arrow.fx.IO
import io.viewpoint.moviedatabase.model.api.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    fun getPopular(@Query("page") page: Int): IO<MovieListResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("page") page: Int): IO<MovieListResponse>

    @GET("movie/upcoming")
    fun getUpcoming(@Query("page") page: Int): IO<MovieListResponse>

    @GET("movie/top_rated")
    fun getTopRated(@Query("page") page: Int): IO<MovieListResponse>
}