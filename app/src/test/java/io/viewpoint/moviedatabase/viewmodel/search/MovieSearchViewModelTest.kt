package io.viewpoint.moviedatabase.viewmodel.search

import androidx.lifecycle.asFlow
import androidx.paging.AsyncPagingDataDiffer
import arrow.fx.IO
import arrow.fx.extensions.fx
import io.mockk.every
import io.mockk.spyk
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
    private val searchApi = spyk(TestSearchApi())
    private val pager =
        MovieSearchPager(
            MovieDatabaseConfigurationRepository(
                TestConfigurationApi()
            ),
            MovieDatabaseSearchRepository(searchApi)
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

    @Test
    fun searchErrorTest() = runBlocking {
        every { searchApi.searchMovie(any(), any()) }
            .returns(IO.fx {
                throw IllegalStateException()
            })

        vm.keyword.value = "test"
        vm.searchCommand.action()

        val pagingData = vm.results
            .asFlow()
            .first()

        assertNotNull(pagingData)

        withTimeoutOrNull(1500) {
            differ.submitData(pagingData)
        }

        assertEquals(0, differ.itemCount)
    }
}