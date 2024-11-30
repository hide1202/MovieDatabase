package io.viewpoint.moviedatabase.test.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.model.api.MovieListResponse
import io.viewpoint.moviedatabase.test.common.MoshiReader
import io.viewpoint.moviedatabase.test.common.ResponseReader

class TestMovieApi : MovieApi {
    override fun getPopular(page: Int): IO<MovieListResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getNowPlaying(page: Int): IO<MovieListResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getUpcoming(page: Int): IO<MovieListResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getTopRated(page: Int): IO<MovieListResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }
}