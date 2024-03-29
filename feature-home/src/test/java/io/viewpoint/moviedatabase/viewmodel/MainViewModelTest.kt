package io.viewpoint.moviedatabase.viewmodel

import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.domain.Languages
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseMovieRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseWantToSeeRepository
import io.viewpoint.moviedatabase.test.TestBase
import io.viewpoint.moviedatabase.test.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.test.mock.TestMovieApi
import io.viewpoint.moviedatabase.test.mock.TestMovieDetailApi
import io.viewpoint.moviedatabase.test.mock.TestPreferencesService
import io.viewpoint.moviedatabase.test.mock.TestWantToSeeDao
import kotlinx.coroutines.runBlocking
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import java.util.Locale

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
                TestMovieDetailApi(),
                TestWantToSeeDao()
            ),
            MovieDatabaseMovieRepository(
                TestMovieApi()
            )
        )

    @Test
    fun `load movie lists when initialize view`() = runBlocking {
        val vm = vm.awaitInit()
        val popularList = vm.uiState.value.popularList
        val nowPlayingList = vm.uiState.value.nowPlayingList
        val upcomingList = vm.uiState.value.upcomingList
        val topRatedList = vm.uiState.value.topRatedList

        expectThat(popularList) {
            get { this.size }.isEqualTo(2)
        }
        expectThat(nowPlayingList) {
            get { this.size }.isEqualTo(2)
        }
        expectThat(upcomingList) {
            get { this.size }.isEqualTo(2)
        }
        expectThat(topRatedList) {
            get { this.size }.isEqualTo(2)
        }
    }

    @Test
    fun `load saved language when initialize view`(): Unit = runBlocking {
        val originalLocale = Locale.getDefault()
        val originalApiLanguage = MovieDatabaseApi.language

        try {
            Locale.setDefault(Languages.SUPPORTED_LANGUAGE_CODES[0])
            MovieDatabaseApi.language = null

            val preferencesService = TestPreferencesService()

            val firstPreLanguage = MovieDatabaseApi.language
            createVmWith(preferencesService).awaitInit()
            expectThat(firstPreLanguage).isNotEqualTo(Languages.SUPPORTED_LANGUAGE_CODES[0].language)

            val expectedLanguage = TestConfigurationApi()
                .getSupportedLanguages()
                .suspended()
                .map { it.iso_639_1 }
                .first { it == Languages.SUPPORTED_LANGUAGE_CODES[1].language }
            preferencesService.putValue(
                PreferencesKeys.SELECTED_LANGUAGE_ISO,
                expectedLanguage
            )

            val secondPreLanguage = MovieDatabaseApi.language
            createVmWith(preferencesService).awaitInit()
            expectThat(secondPreLanguage).isNotEqualTo(MovieDatabaseApi.language)
            expectThat(MovieDatabaseApi.language).isEqualTo(expectedLanguage)
        } finally {
            Locale.setDefault(originalLocale)
            MovieDatabaseApi.language = originalApiLanguage
        }
    }
}