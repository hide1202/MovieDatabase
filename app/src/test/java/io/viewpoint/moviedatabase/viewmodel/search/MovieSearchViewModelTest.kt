package io.viewpoint.moviedatabase.viewmodel.search

import androidx.lifecycle.asFlow
import androidx.paging.AsyncPagingDataDiffer
import arrow.fx.IO
import arrow.fx.extensions.fx
import io.mockk.every
import io.mockk.spyk
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.getValues
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseSearchRepository
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.test.TestBase
import io.viewpoint.moviedatabase.test.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.test.mock.TestPreferencesService
import io.viewpoint.moviedatabase.test.mock.TestSearchApi
import io.viewpoint.moviedatabase.ui.search.viewmodel.MovieSearchPager
import io.viewpoint.moviedatabase.ui.search.viewmodel.MovieSearchViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.*

//@RunWith(RobolectricTestRunner::class)
//@Config(application = TestApplication::class)
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

    private val differ: AsyncPagingDataDiffer<SearchResultModel> =
        io.viewpoint.moviedatabase.test.asyncPagingDataDiffer()

    @Before
    fun setUp() {
        vm = MovieSearchViewModel(preferences, pager)
    }

    @Test
    fun searchTest() {
        testScope.launch {
            val keyword = "test"
            vm.keyword.value = keyword
            vm.searchCommand()

            val pagingData = vm.results
                .asFlow()
                .first()

            val submitJob = GlobalScope.launch {
                differ.submitData(pagingData)
            }
            try {
                io.viewpoint.moviedatabase.test.tryWithDelay {
                    if (differ.itemCount > 0) {
                        differ.getItem(0)
                        true
                    } else {
                        false
                    }
                }

                expectThat(differ.itemCount).isGreaterThan(0)
                expectThat(preferences.getValues(PreferencesKeys.SEARCHED_KEYWORDS))
                    .contains(keyword)
            } finally {
                submitJob.cancel()
            }
        }
    }

    @Test
    fun removeKeywordTest() {
        testScope.launch {
            val keyword = "test"
            vm.keyword.value = keyword
            vm.searchCommand()

            // TODO Remove delays
            delay(800L)
            val before = vm.recentKeywords.value?.any { it == "test" } == true

            vm.removeRecentKeyword("test")

            delay(800L)
            val after = vm.recentKeywords.value?.none { it == "test" } == true

            expectThat(before).isTrue()
            expectThat(after).isTrue()
        }
    }

    @Test
    fun searchEmptyKeywordTest() {
        testScope.launch {
            val keyword = ""
            vm.keyword.value = keyword
            vm.searchCommand.action()

            val pagingData = withTimeoutOrNull(1500) {
                vm.results
                    .asFlow()
                    .first()
            }

            expectThat(pagingData).isNull()
            expectThat(preferences.getValues(PreferencesKeys.SEARCHED_KEYWORDS))
                .doesNotContain(keyword)
        }
    }

    @Test
    fun searchErrorTest() {
        testScope.launch {
            every { searchApi.searchMovie(any(), any()) }
                .returns(IO.fx {
                    throw IllegalStateException()
                })

            vm.keyword.value = "test"
            vm.searchCommand.action()

            val pagingData = vm.results
                .asFlow()
                .first()

            withTimeoutOrNull(1500) {
                differ.submitData(pagingData)
            }

            expectThat(differ.itemCount).isEqualTo(0)
        }
    }
}