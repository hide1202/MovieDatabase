package io.viewpoint.moviedatabase.api

import arrow.fx.IO
import io.viewpoint.moviedatabase.model.api.MoviePopularResponse
import retrofit2.http.GET

interface MovieApi {
    @GET("movie/popular")
    fun getPopular(): IO<MoviePopularResponse>
}