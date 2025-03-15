package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.api.dto.MovieDto
import io.viewpoint.moviedatabase.model.common.PagingResult

interface SearchRepository {
    suspend fun searchKeyword(
        keyword: String,
        page: Int?
    ): PagingResult<Int, MovieDto>
}