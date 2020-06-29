package io.viewpoint.moviedatabase.platform.ui.search.paging

import androidx.paging.PagingSource
import io.viewpoint.moviedatabase.api.SearchApi
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapper
import io.viewpoint.moviedatabase.model.ui.SearchResultModel

class MovieSearchPagingSource(
    private val keyword: String,
    private val searchApi: SearchApi,
    private val configurationRepository: ConfigurationRepository
) : PagingSource<Int, SearchResultModel>() {
    private val mapper: SearchResultMapper = SearchResultMapper(configurationRepository)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultModel> {
        val position = params.key ?: INITIAL_PAGE
        return try {
            val results = searchApi
                .searchMovie(keyword, position)
                .suspended()
                .results
                .map {
                    mapper.map(it)
                }

            LoadResult.Page(
                data = results,
                prevKey = if (position == INITIAL_PAGE) null else position - 1,
                nextKey = if (results.isEmpty()) null else position + 1
            )
        } catch (throwable: Throwable) {
            return LoadResult.Error(throwable)
        }
    }

    companion object {
        const val INITIAL_PAGE = 1
    }
}
