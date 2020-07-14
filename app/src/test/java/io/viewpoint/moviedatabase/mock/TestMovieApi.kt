package io.viewpoint.moviedatabase.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.model.api.MovieListResponse
import io.viewpoint.moviedatabase.util.MoshiReader
import io.viewpoint.moviedatabase.util.ResponseReader

class TestMovieApi : MovieApi {
    override fun getPopular(): IO<MovieListResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "/responses/movie-list-results.json",
                MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getNowPlaying(): IO<MovieListResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "/responses/movie-list-results.json",
                MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getUpcoming(): IO<MovieListResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "/responses/movie-list-results.json",
                MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getTopRated(): IO<MovieListResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "/responses/movie-list-results.json",
                MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }
}