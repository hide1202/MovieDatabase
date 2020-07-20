package io.viewpoint.moviedatabase.platform.ui.search

import androidx.lifecycle.asFlow
import androidx.paging.PagingData
import io.viewpoint.moviedatabase.TestBase
import io.viewpoint.moviedatabase.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.mock.TestSearchApi
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.viewmodel.search.MovieSearchPager
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseSearchRepository
import io.viewpoint.moviedatabase.viewmodel.search.MovieSearchViewModel
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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

    @Test
    fun searchTest() = runBlocking {
        vm.keyword.value = "test"
        vm.searchCommand.action()

        val pagingData = vm.results
            .asFlow()
            .first()
        assertNotNull(pagingData)
        assertTrue(pagingData != PagingData.empty<SearchResultModel>())
    }
}