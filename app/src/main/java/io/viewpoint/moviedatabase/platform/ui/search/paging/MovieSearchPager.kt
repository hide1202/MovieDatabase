package io.viewpoint.moviedatabase.platform.ui.search.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.viewpoint.moviedatabase.api.SearchApi
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieSearchPager @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    private val searchApi: SearchApi
) {
    fun pagingWithKeyword(keyword: String): Flow<PagingData<SearchResultModel>> =
        Pager(PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MovieSearchPagingSource(
                    keyword = keyword,
                    searchApi = searchApi,
                    configurationRepository = configurationRepository
                )
            }).flow
}