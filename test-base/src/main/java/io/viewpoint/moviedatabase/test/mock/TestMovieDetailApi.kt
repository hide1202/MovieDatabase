package io.viewpoint.moviedatabase.test.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.MovieDetailApi
import io.viewpoint.moviedatabase.model.api.*
import io.viewpoint.moviedatabase.test.MoshiReader
import io.viewpoint.moviedatabase.test.ResponseReader

class TestMovieDetailApi : MovieDetailApi {
    override fun getMovieDetail(id: Int): IO<MovieDetail> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "responses/movie-detail.json",
                MoshiReader.moshi.adapter(MovieDetail::class.java)
            )
        }
    }

    override fun getMovieCredits(id: Int): IO<CreditsResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "responses/movie-credits.json",
                MoshiReader.moshi.adapter(CreditsResponse::class.java)
            )
        }
    }

    override fun getKeywords(id: Int): IO<KeywordResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "responses/movie-keywords.json",
                MoshiReader.moshi.adapter(KeywordResponse::class.java)
            )
        }
    }

    override fun getRecommendations(id: Int): IO<MovieListResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "responses/movie-recommendations.json",
                MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
    }

    override fun getWatchProviders(id: Int): IO<WatchProviderResponse> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "responses/movie-watch-providers.json",
                MoshiReader.moshi.adapter(WatchProviderResponse::class.java)
            )
        }
    }
}