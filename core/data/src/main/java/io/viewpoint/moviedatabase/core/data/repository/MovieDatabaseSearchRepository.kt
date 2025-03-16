package io.viewpoint.moviedatabase.core.data.repository

import io.viewpoint.moviedatabase.api.SearchApi
import io.viewpoint.moviedatabase.core.common.coroutines.suspendRunCatching
import io.viewpoint.moviedatabase.domain.model.Movie
import io.viewpoint.moviedatabase.domain.repository.SearchRepository
import io.viewpoint.moviedatabase.model.common.PagingResult
import javax.inject.Inject

class MovieDatabaseSearchRepository @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun searchKeyword(keyword: String, page: Int?): PagingResult<Int, Movie> {
        val page = page ?: INITIAL_PAGE
        return suspendRunCatching {
            searchApi.searchMovie(keyword, page)
        }.map {
            PagingResult.Success(
                data = it.results.map { it.asDomain() },
                previousKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if (it.results.isEmpty()) null else page + 1
            )
        }.getOrElse {
            PagingResult.Error(it)
        }
    }

    companion object {
        const val INITIAL_PAGE = 1
    }
}