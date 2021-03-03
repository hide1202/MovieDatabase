package io.viewpoint.moviedatabase.viewmodel.search

import androidx.lifecycle.asFlow
import androidx.paging.AsyncPagingDataDiffer
import arrow.fx.IO
import arrow.fx.extensions.fx
import io.mockk.every
import io.mockk.spyk
import io.viewpoint.moviedatabase.TestBase
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.getValues
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseSearchRepository
import io.viewpoint.moviedatabase.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.mock.TestPreferencesService
import io.viewpoint.moviedatabase.mock.TestSearchApi
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.tryWithDelay
import io.viewpoint.moviedatabase.ui.search.viewmodel.MovieSearchPager
import io.viewpoint.moviedatabase.ui.search.viewmodel.MovieSearchViewModel
import io.viewpoint.moviedatabase.util.asyncPagingDataDiffer
import junit.framework.Assert.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Before
import org.junit.Test

class MovieSearchViewModelTest : TestBase() {
    private val preferences = TestPreferencesService()
    private val searchApi = spyk(TestSearchApi())
    private val pager =
        MovieSearchPager(
            MovieDatabaseConfigurationRepository(
                TestConfigurationApi()
            ),
            MovieDatabaseSearchRepository(searchApi)
        )
    private lateinit var vm: MovieSearchViewModel

    private val differ: AsyncPagingDataDiffer<SearchResultModel> = asyncPagingDataDiffer()

    @Before
    fun setUp() {
        vm = MovieSearchViewModel(preferences, pager)
    }

    @Test
    fun searchTest() = runBlocking {
        val keyword = "test"
        vm.keyword.value = keyword
        vm.searchCommand()

        val pagingData = vm.results
            .asFlow()
            .first()

        assertNotNull(pagingData)

        val submitJob = GlobalScope.launch {
            differ.submitData(pagingData)
        }
        try {
            tryWithDelay {
                if (differ.itemCount > 0) {
                    differ.getItem(0)
                    true
                } else {
                    false
                }
            }

            assertTrue(differ.itemCount > 0)
            assertTrue(preferences.getValues(PreferencesKeys.SEARCHED_KEYWORDS).contains(keyword))
        } finally {
            submitJob.cancel()
        }
    }

    @Test
    fun removeKeywordTest() = runBlocking {
        val keyword = "test"
        vm.keyword.value = keyword
        vm.searchCommand()

        val before = vm.recentKeywords.value?.any { it == "test" } == true
        vm.removeRecentKeyword("test")
        val after = vm.recentKeywords.value?.any { it == "test" } == true
        assertTrue(before)
        assertTrue(after)
    }

    @Test
    fun searchEmptyKeywordTest() = runBlocking {
        val keyword = ""
        vm.keyword.value = keyword
        vm.searchCommand.action()

        val pagingData = withTimeoutOrNull(1500) {
            vm.results
                .asFlow()
                .first()
        }

        assertNull(pagingData)
        assertFalse(preferences.getValues(PreferencesKeys.SEARCHED_KEYWORDS).contains(keyword))
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