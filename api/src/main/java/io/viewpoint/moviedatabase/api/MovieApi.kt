package io.viewpoint.moviedatabase.api

import arrow.fx.IO
import io.viewpoint.moviedatabase.model.api.MovieListResponse
import retrofit2.http.GET

interface MovieApi {
    @GET("movie/popular")
    fun getPopular(): IO<MovieListResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(): IO<MovieListResponse>

    @GET("movie/upcoming")
    fun getUpcoming(): IO<MovieListResponse>

    @GET("movie/top_rated")
    fun getTopRated(): IO<MovieListResponse>
}