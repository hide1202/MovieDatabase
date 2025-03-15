package io.viewpoint.moviedatabase.test.mock

import io.viewpoint.moviedatabase.api.SearchApi
import io.viewpoint.moviedatabase.api.dto.MovieListResponse
import io.viewpoint.moviedatabase.test.common.MoshiReader
import io.viewpoint.moviedatabase.test.common.ResponseReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TestSearchApi : SearchApi {
    override suspend fun searchMovie(query: String, page: Int): MovieListResponse =
        withContext(Dispatchers.IO) {
            ResponseReader.jsonFromFileAsync(
                "responses/search-results.json",
                MoshiReader.moshi.adapter(MovieListResponse::class.java)
            )
        }
}