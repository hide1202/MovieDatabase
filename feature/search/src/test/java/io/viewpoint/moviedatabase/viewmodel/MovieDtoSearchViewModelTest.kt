package io.viewpoint.moviedatabase.viewmodel

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.testing.asSnapshot
import io.mockk.coEvery
import io.mockk.spyk
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.getValues
import io.viewpoint.moviedatabase.core.data.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.core.data.repository.MovieDatabaseSearchRepository
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.test.TestBase
import io.viewpoint.moviedatabase.test.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.test.mock.TestPreferencesService
import io.viewpoint.moviedatabase.test.mock.TestSearchApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan
import strikt.assertions.isTrue

//@RunWith(RobolectricTestRunner::class)
//@Config(application = TestApplication::class)
class MovieDtoSearchViewModelTest : TestBase() {
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
        runTest {
            val keyword = "test"
            vm.onKeywordChanged(keyword)
            vm.searchCommand()

            val snapshot = vm.results.asSnapshot()
            expectThat(snapshot.size).isGreaterThan(0)
            expectThat(preferences.getValues(PreferencesKeys.SEARCHED_KEYWORDS))
                .contains(keyword)
        }
    }

    @Test
    fun removeKeywordTest() {
        runTest {
            val keyword = "test"
            vm.onKeywordChanged(keyword)
            vm.searchCommand()

            vm.results.asSnapshot()

            val before = vm.recentKeywords.value.any { it == "test" } == true

            vm.removeRecentKeyword("test")

            val after = vm.recentKeywords.value.none { it == "test" } == true

            expectThat(before).isTrue()
            expectThat(after).isTrue()
        }
    }

    @Test
    fun searchErrorTest() {
        runTest {
            coEvery { searchApi.searchMovie(any(), any()) }
                .throws(IllegalStateException())

            vm.onKeywordChanged("test")
            vm.searchCommand.action()

            val pagingData = vm.results
                .first()

            withTimeoutOrNull(1500) {
                differ.submitData(pagingData)
            }

            expectThat(differ.itemCount).isEqualTo(0)
        }
    }
}