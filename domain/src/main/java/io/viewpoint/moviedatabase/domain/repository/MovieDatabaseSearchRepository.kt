package io.viewpoint.moviedatabase.domain.repository

import arrow.core.getOrHandle
import io.viewpoint.moviedatabase.api.SearchApi
import io.viewpoint.moviedatabase.model.api.Movie
import io.viewpoint.moviedatabase.model.common.PagingResult
import javax.inject.Inject

class MovieDatabaseSearchRepository @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun searchKeyword(keyword: String, page: Int?): PagingResult<Int, Movie> {
        val page = page ?: INITIAL_PAGE
        return searchApi.searchMovie(keyword, page)
            .attempt()
            .suspended()
            .map<PagingResult<Int, Movie>> {
                PagingResult.Success(
                    data = it.results,
                    previousKey = if (page == INITIAL_PAGE) null else page - 1,
                    nextKey = if (it.results.isEmpty()) null else page + 1
                )
            }
            .getOrHandle {
                PagingResult.Error(it)
            }
    }

    companion object {
        const val INITIAL_PAGE = 1
    }
}