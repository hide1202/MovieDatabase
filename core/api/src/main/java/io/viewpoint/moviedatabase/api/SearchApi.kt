package io.viewpoint.moviedatabase.api

import io.viewpoint.moviedatabase.model.api.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): MovieListResponse
}