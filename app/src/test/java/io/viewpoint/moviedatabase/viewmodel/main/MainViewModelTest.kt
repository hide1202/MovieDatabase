package io.viewpoint.moviedatabase.viewmodel.main

import androidx.lifecycle.Observer
import io.viewpoint.moviedatabase.TestBase
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseMovieRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseWantToSeeRepository
import io.viewpoint.moviedatabase.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.mock.TestMovieApi
import io.viewpoint.moviedatabase.mock.TestPreferencesService
import io.viewpoint.moviedatabase.mock.TestWantToSeeDao
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MainViewModelTest : TestBase() {
    private val vm = MainViewModel(
        TestPreferencesService(),
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
}