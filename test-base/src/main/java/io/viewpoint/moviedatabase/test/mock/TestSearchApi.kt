package io.viewpoint.moviedatabase.test.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.SearchApi
import io.viewpoint.moviedatabase.model.api.MovieListResponse

class TestSearchApi : SearchApi {
    override fun searchMovie(query: String, page: Int): IO<MovieListResponse> =
        IO.fx {
            !effect {
                io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                    "responses/search-results.json",
                    io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(MovieListResponse::class.java)
                )
            }
        }
}