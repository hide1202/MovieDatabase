package io.viewpoint.moviedatabase.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.SearchApi
import io.viewpoint.moviedatabase.model.api.MovieSearchResponse
import io.viewpoint.moviedatabase.util.MoshiReader
import io.viewpoint.moviedatabase.util.ResponseReader

class TestSearchApi : SearchApi {
    override fun searchMovie(query: String, page: Int): IO<MovieSearchResponse> =
        IO.fx {
            !effect {
                ResponseReader.jsonFromFileAsync(
                    "/responses/search-results.json",
                    MoshiReader.moshi.adapter(MovieSearchResponse::class.java)
                )
            }
        }
}