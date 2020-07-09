package io.viewpoint.moviedatabase.platform.ui.search.paging

import androidx.paging.PagingSource
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.SearchRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapper
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.util.LoadResultMapper

class MovieSearchPagingSource(
    private val keyword: String,
    private val searchRepository: SearchRepository,
    configurationRepository: ConfigurationRepository
) : PagingSource<Int, SearchResultModel>() {
    private val mapper: SearchResultMapper = SearchResultMapper(configurationRepository)
    private val resultMapper: LoadResultMapper<Int, SearchResultModel> = LoadResultMapper()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultModel> =
        searchRepository.searchKeyword(keyword, params.key)
            .map {
                mapper.map(it)
            }
            .let {
                resultMapper.map(it)
            }
}
