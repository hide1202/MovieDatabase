package io.viewpoint.moviedatabase.test.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.model.api.CreditsResponse
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.model.api.MovieListResponse

class TestMovieApi : MovieApi {
    override fun getMovieDetail(id: Int): IO<MovieDetail> = IO.fx {
        !effect {
            io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                "responses/movie-detail.json",
                io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(MovieDetail::class.java)
            )
        }
    }

    override fun getPopular(): IO<MovieListResponse> = IO.fx {
        !effect {
            io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getNowPlaying(): IO<MovieListResponse> = IO.fx {
        !effect {
            io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getUpcoming(): IO<MovieListResponse> = IO.fx {
        !effect {
            io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getTopRated(): IO<MovieListResponse> = IO.fx {
        !effect {
            io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                "responses/movie-list-results.json",
                io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getMovieCredits(id: Int): IO<CreditsResponse> = IO.fx {
        !effect {
            io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                "responses/movie-credits.json",
                io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(CreditsResponse::class.java)
            )
        }
    }
}