package io.viewpoint.moviedatabase.api

import arrow.fx.IO
import io.viewpoint.moviedatabase.model.api.CreditsResponse
import io.viewpoint.moviedatabase.model.api.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailApi {
    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id: Int): IO<MovieDetail>

    @GET("movie/{id}/credits")
    fun getMovieCredits(@Path("id") id: Int): IO<CreditsResponse>
}