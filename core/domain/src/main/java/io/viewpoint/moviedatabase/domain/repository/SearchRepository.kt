package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.model.api.Movie
import io.viewpoint.moviedatabase.model.common.PagingResult

interface SearchRepository {
    suspend fun searchKeyword(
        keyword: String,
        page: Int?
    ): PagingResult<Int, Movie>
}