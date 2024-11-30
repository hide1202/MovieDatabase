package io.viewpoint.moviedatabase.test.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.SearchApi
import io.viewpoint.moviedatabase.model.api.MovieListResponse
import io.viewpoint.moviedatabase.test.common.MoshiReader
import io.viewpoint.moviedatabase.test.common.ResponseReader

class TestSearchApi : SearchApi {
    override fun searchMovie(query: String, page: Int): IO<MovieListResponse> =
        IO.fx {
            !effect {
                ResponseReader.jsonFromFileAsync(
                    "responses/search-results.json",
                    MoshiReader.moshi.adapter(MovieListResponse::class.java)
                )
            }
        }
}