package io.viewpoint.moviedatabase.test.mock

import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.api.dto.MovieListResponse
import io.viewpoint.moviedatabase.test.common.MoshiReader
import io.viewpoint.moviedatabase.test.common.ResponseReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TestMovieApi : MovieApi {
    override suspend fun getPopular(page: Int): MovieListResponse = withContext(Dispatchers.IO) {
        ResponseReader.jsonFromFileAsync(
            "responses/movie-list-results.json",
            MoshiReader.moshi.adapter(MovieListResponse::class.java)
        )
    }

    override suspend fun getNowPlaying(page: Int): MovieListResponse = withContext(Dispatchers.IO) {
        ResponseReader.jsonFromFileAsync(
            "responses/movie-list-results.json",
            MoshiReader.moshi.adapter(MovieListResponse::class.java)
        )
    }

    override suspend fun getUpcoming(page: Int): MovieListResponse = withContext(Dispatchers.IO) {
        ResponseReader.jsonFromFileAsync(
            "responses/movie-list-results.json",
            MoshiReader.moshi.adapter(MovieListResponse::class.java)
        )
    }

    override suspend fun getTopRated(page: Int): MovieListResponse = withContext(Dispatchers.IO) {
        ResponseReader.jsonFromFileAsync(
            "responses/movie-list-results.json",
            MoshiReader.moshi.adapter(MovieListResponse::class.java)
        )
    }
}