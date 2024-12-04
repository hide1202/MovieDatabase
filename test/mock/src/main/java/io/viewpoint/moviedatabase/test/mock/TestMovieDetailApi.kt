package io.viewpoint.moviedatabase.test.mock

import io.viewpoint.moviedatabase.api.MovieDetailApi
import io.viewpoint.moviedatabase.model.api.CreditsResponse
import io.viewpoint.moviedatabase.model.api.KeywordResponse
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.model.api.MovieListResponse
import io.viewpoint.moviedatabase.model.api.WatchProviderResponse
import io.viewpoint.moviedatabase.test.common.MoshiReader
import io.viewpoint.moviedatabase.test.common.ResponseReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TestMovieDetailApi : MovieDetailApi {
    override suspend fun getMovieDetail(id: Int): MovieDetail = withContext(Dispatchers.IO) {
        ResponseReader.jsonFromFileAsync(
            "responses/movie-detail.json",
            MoshiReader.moshi.adapter(MovieDetail::class.java)
        ).takeIf {
            it.id == id
        } ?: throw NoSuchElementException()
    }

    override suspend fun getMovieCredits(id: Int): CreditsResponse = withContext(Dispatchers.IO) {
        ResponseReader.jsonFromFileAsync(
            "responses/movie-credits.json",
            MoshiReader.moshi.adapter(CreditsResponse::class.java)
        ).takeIf {
            it.id == id
        } ?: throw NoSuchElementException()
    }

    override suspend fun getKeywords(id: Int): KeywordResponse = withContext(Dispatchers.IO) {
        ResponseReader.jsonFromFileAsync(
            "responses/movie-keywords.json",
            MoshiReader.moshi.adapter(KeywordResponse::class.java)
        ).takeIf {
            it.id == id
        } ?: throw NoSuchElementException()
    }

    override suspend fun getRecommendations(id: Int): MovieListResponse =
        withContext(Dispatchers.IO) {
            if (id != VALID_ID) {
                throw NoSuchElementException()
            }
            ResponseReader.jsonFromFileAsync(
                "responses/movie-recommendations.json",
                MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }

    override suspend fun getWatchProviders(id: Int): WatchProviderResponse =
        withContext(Dispatchers.IO) {
            ResponseReader.jsonFromFileAsync(
                "responses/movie-watch-providers.json",
                MoshiReader.moshi.adapter(WatchProviderResponse::class.java)
            ).takeIf {
                it.id == id
            } ?: throw NoSuchElementException()
        }

    companion object {
        const val VALID_ID = 557
    }
}