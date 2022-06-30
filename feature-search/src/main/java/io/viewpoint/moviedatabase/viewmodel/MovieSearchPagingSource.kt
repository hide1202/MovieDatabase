package io.viewpoint.moviedatabase.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.SearchRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapperProvider
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.domain.LoadResultMapper

class MovieSearchPagingSource(
    private val keyword: String,
    private val searchRepository: SearchRepository,
    configurationRepository: ConfigurationRepository
) : PagingSource<Int, SearchResultModel>() {
    private val mapperProvider: SearchResultMapperProvider =
        SearchResultMapperProvider(configurationRepository)
    private val resultMapper: LoadResultMapper<Int, SearchResultModel> = LoadResultMapper()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultModel> =
        searchRepository.searchKeyword(keyword, params.key)
            .map {
                mapperProvider.mapperFromMovie.map(it)
            }
            .let {
                resultMapper.map(it)
            }

    override fun getRefreshKey(state: PagingState<Int, SearchResultModel>): Int? = null
}
