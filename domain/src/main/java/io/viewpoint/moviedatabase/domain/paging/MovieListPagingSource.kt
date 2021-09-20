package io.viewpoint.moviedatabase.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.viewpoint.moviedatabase.domain.LoadResultMapper
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapperProvider
import io.viewpoint.moviedatabase.model.api.Movie
import io.viewpoint.moviedatabase.model.common.PagingResult
import io.viewpoint.moviedatabase.model.ui.SearchResultModel

class MovieListPagingSource(
    configurationRepository: ConfigurationRepository,
    private val loader: suspend (Int) -> List<Movie>
) : PagingSource<Int, SearchResultModel>() {
    private val mapperProvider: SearchResultMapperProvider =
        SearchResultMapperProvider(configurationRepository)
    private val resultMapper: LoadResultMapper<Int, SearchResultModel> = LoadResultMapper()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultModel> {
        val page = params.key ?: INITIAL_PAGE
        return loader(page)
            .let {
                PagingResult.Success(
                    data = it,
                    previousKey = if (page == INITIAL_PAGE) null else page - 1,
                    nextKey = if (it.isEmpty()) null else page + 1
                )

            }
            .map {
                mapperProvider.mapperFromMovie.map(it)
            }
            .let {
                resultMapper.map(it)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchResultModel>): Int? = null

    companion object {
        private const val INITIAL_PAGE = 1
    }
}