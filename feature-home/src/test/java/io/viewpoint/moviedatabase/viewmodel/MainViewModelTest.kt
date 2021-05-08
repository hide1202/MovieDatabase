package io.viewpoint.moviedatabase.viewmodel

import androidx.lifecycle.Observer
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseMovieRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseWantToSeeRepository
import io.viewpoint.moviedatabase.test.TestBase
import io.viewpoint.moviedatabase.test.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.test.mock.TestMovieApi
import io.viewpoint.moviedatabase.test.mock.TestPreferencesService
import io.viewpoint.moviedatabase.test.mock.TestWantToSeeDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class MainViewModelTest : TestBase() {
    private val vm
        get() = createVmWith(TestPreferencesService())

    private fun createVmWith(preferencesService: PreferencesService): MainViewModel =
        MainViewModel(
            preferencesService,
            MovieDatabaseConfigurationRepository(
                TestConfigurationApi()
            ),
            MovieDatabaseWantToSeeRepository(
                TestMovieApi(),
                TestWantToSeeDao()
            ),
            MovieDatabaseMovieRepository(
                TestMovieApi()
            )
        )

    @Test
    fun `load movie lists when initialize view`() = runBlocking {
        val vm = vm.awaitInit()
        val wantToSeeList = vm.wantToSee.value
        val popularList = vm.popular.value
        val nowPlayingList = vm.nowPlaying.value
        val upcomingList = vm.upcoming.value
        val topRatedList = vm.topRated.value

        expectThat(wantToSeeList).isNotNull()
        expectThat(popularList) {
            isNotNull()
            get { this?.size }.isEqualTo(2)
        }
        expectThat(nowPlayingList) {
            isNotNull()
            get { this?.size }.isEqualTo(2)
        }
        expectThat(upcomingList) {
            isNotNull()
            get { this?.size }.isEqualTo(2)
        }
        expectThat(topRatedList) {
            isNotNull()
            get { this?.size }.isEqualTo(2)
        }
    }

    @Test
    fun `loadData will change isLoading property`() = runBlocking {
        val vm = vm.awaitInit()

        val set = mutableSetOf<Boolean>()
        val observer = Observer<Boolean> {
            set += it
        }
        vm.isLoading.observeForever(observer)

        vm.loadData()

        delay(1500L)
        expectThat(set.size).isEqualTo(2)

        vm.isLoading.removeObserver(observer)
    }

    @Test
    fun `load saved language when initialize view`(): Unit = runBlocking {
        val preferencesService = TestPreferencesService()

        val firstPreLanguage = MovieDatabaseApi.language
        createVmWith(preferencesService).awaitInit()
        expectThat(firstPreLanguage).isNull()

        val expectedLanguage = TestConfigurationApi()
            .getSupportedLanguages()
            .suspended()
            .map { it.iso_639_1 }
            .first { it == "en" }
        preferencesService.putValue(
            PreferencesKeys.SELECTED_LANGUAGE_ISO,
            expectedLanguage
        )

        val secondPreLanguage = MovieDatabaseApi.language
        createVmWith(preferencesService).awaitInit()
        expectThat(secondPreLanguage).isNotEqualTo(MovieDatabaseApi.language)
        expectThat(MovieDatabaseApi.language).isEqualTo(expectedLanguage)
    }
}