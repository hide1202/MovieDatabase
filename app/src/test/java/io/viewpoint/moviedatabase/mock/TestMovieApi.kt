package io.viewpoint.moviedatabase.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.model.api.MoviePopularResponse
import io.viewpoint.moviedatabase.util.MoshiReader
import io.viewpoint.moviedatabase.util.ResponseReader

class TestMovieApi : MovieApi {
    override fun getPopular(): IO<MoviePopularResponse> {
        return IO.fx {
            !effect {
                ResponseReader.jsonFromFileAsync(
                    "/responses/popular-results.json",
                    MoshiReader.moshi.adapter(MoviePopularResponse::class.java)
                )
            }
        }
    }
}