package io.viewpoint.moviedatabase.platform.ui.search.paging

import androidx.paging.PagingSource
import io.viewpoint.moviedatabase.api.SearchApi
import io.viewpoint.moviedatabase.platform.ui.search.model.SearchResultModel

class MovieSearchPagingSource(
    private val keyword: String,
    private val searchApi: SearchApi
) : PagingSource<Int, SearchResultModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultModel> {
        val position = params.key ?: INITIAL_PAGE
        return try {
            val results = searchApi
                .searchMovie(keyword, position)
                .suspended()
                .results
                .map {
                    SearchResultModel(
                        id = it.id,
                        title = it.title,
                        overview = it.overview
                    )
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
