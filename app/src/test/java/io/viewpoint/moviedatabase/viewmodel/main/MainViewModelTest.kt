package io.viewpoint.moviedatabase.viewmodel.main

import androidx.lifecycle.Observer
import io.viewpoint.moviedatabase.TestBase
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseMovieRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseWantToSeeRepository
import io.viewpoint.moviedatabase.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.mock.TestMovieApi
import io.viewpoint.moviedatabase.mock.TestPreferencesService
import io.viewpoint.moviedatabase.mock.TestWantToSeeDao
import io.viewpoint.moviedatabase.ui.home.viewmodel.MainViewModel
import junit.framework.Assert.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

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
        assertNotNull(wantToSeeList)
        assertNotNull(popularList)
        assertNotNull(nowPlayingList)
        assertNotNull(upcomingList)
        assertNotNull(topRatedList)
        assertEquals(2, requireNotNull(popularList).size)
        assertEquals(2, requireNotNull(nowPlayingList).size)
        assertEquals(2, requireNotNull(upcomingList).size)
        assertEquals(2, requireNotNull(topRatedList).size)
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
        assertEquals(2, set.size)

        vm.isLoading.removeObserver(observer)
    }

    @Test
    fun `load saved language when initialize view`() = runBlocking {
        val preferencesService = TestPreferencesService()

        val firstPreLanguage = MovieDatabaseApi.language
        createVmWith(preferencesService).awaitInit()
        assertNull(firstPreLanguage)

        val expectedLanguage = TestConfigurationApi()
            .getSupportedLanguages()
            .suspended()[0]
            .iso_639_1
        preferencesService.putValue(
            PreferencesKeys.SELECTED_LANGUAGE_ISO,
            expectedLanguage
        )

        val secondPreLanguage = MovieDatabaseApi.language
        createVmWith(preferencesService).awaitInit()
        assertTrue(secondPreLanguage != MovieDatabaseApi.language)
        assertEquals(expectedLanguage, MovieDatabaseApi.language)
    }
}