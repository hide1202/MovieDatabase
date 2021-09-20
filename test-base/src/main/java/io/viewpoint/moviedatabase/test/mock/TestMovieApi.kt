package io.viewpoint.moviedatabase.test.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.model.api.MovieListResponse

class TestMovieApi : MovieApi {
    override fun getPopular(page: Int): IO<MovieListResponse> = IO.fx {
        !effect {
            io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getNowPlaying(page: Int): IO<MovieListResponse> = IO.fx {
        !effect {
            io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getUpcoming(page: Int): IO<MovieListResponse> = IO.fx {
        !effect {
            io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getTopRated(page: Int): IO<MovieListResponse> = IO.fx {
        !effect {
            io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }
}