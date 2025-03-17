package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.domain.model.Movie
import io.viewpoint.moviedatabase.domain.model.PagingResult

interface SearchRepository {
    suspend fun searchKeyword(
        keyword: String,
        page: Int?
    ): PagingResult<Int, Movie>
}