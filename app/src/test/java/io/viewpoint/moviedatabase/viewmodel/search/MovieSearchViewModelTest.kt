package io.viewpoint.moviedatabase.viewmodel.search

import androidx.lifecycle.asFlow
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import io.viewpoint.moviedatabase.TestBase
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseSearchRepository
import io.viewpoint.moviedatabase.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.mock.TestSearchApi
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.util.asyncPagingDataDiffer
import junit.framework.Assert.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Test

class MovieSearchViewModelTest : TestBase() {
    private val pager =
        MovieSearchPager(
            MovieDatabaseConfigurationRepository(
                TestConfigurationApi()
            ),
            MovieDatabaseSearchRepository(
                TestSearchApi()
            )
        )
    private val vm =
        MovieSearchViewModel(pager)

    private val differ: AsyncPagingDataDiffer<SearchResultModel> = asyncPagingDataDiffer()

    @Test
    fun searchTest() = runBlocking {
        vm.keyword.value = "test"
        vm.searchCommand.action()

        val pagingData = vm.results
            .asFlow()
            .first()

        assertNotNull(pagingData)
        assertTrue(pagingData != PagingData.empty<SearchResultModel>())

        withTimeoutOrNull(1500) {
            differ.submitData(pagingData)
        }

        assertTrue(differ.itemCount > 0)
    }

    @Test
    fun searchEmptyKeywordTest() = runBlocking {
        vm.keyword.value = ""
        vm.searchCommand.action()

        val pagingData = withTimeoutOrNull(1500) {
            vm.results
                .asFlow()
                .first()
        }

        assertNull(pagingData)
    }
}