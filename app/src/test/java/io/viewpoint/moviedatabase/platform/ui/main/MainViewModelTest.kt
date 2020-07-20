package io.viewpoint.moviedatabase.platform.ui.main

import io.viewpoint.moviedatabase.TestBase
import io.viewpoint.moviedatabase.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.mock.TestMovieApi
import io.viewpoint.moviedatabase.mock.TestPreferencesService
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseMovieRepository
import io.viewpoint.moviedatabase.viewmodel.main.MainViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MainViewModelTest : TestBase() {
    private val vm = MainViewModel(
        TestPreferencesService(),
        MovieDatabaseConfigurationRepository(
            TestConfigurationApi()
        ),
        MovieDatabaseMovieRepository(
            TestMovieApi()
        )
    )

    @Test
    fun `load movie lists when initialize view`() = runBlocking {
        val vm = vm.awaitInit()
        val popularList = vm.popular.value
        val nowPlayingList = vm.nowPlaying.value
        val upcomingList = vm.upcoming.value
        val topRatedList = vm.topRated.value
        assertNotNull(popularList)
        assertNotNull(nowPlayingList)
        assertNotNull(upcomingList)
        assertNotNull(topRatedList)
        assertEquals(2, requireNotNull(popularList).size)
        assertEquals(2, requireNotNull(nowPlayingList).size)
        assertEquals(2, requireNotNull(upcomingList).size)
        assertEquals(2, requireNotNull(topRatedList).size)
    }
}