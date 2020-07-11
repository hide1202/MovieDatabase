package io.viewpoint.moviedatabase.platform.ui.main

import io.viewpoint.moviedatabase.TestBase
import io.viewpoint.moviedatabase.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.mock.TestMovieApi
import io.viewpoint.moviedatabase.mock.TestPreferencesService
import io.viewpoint.moviedatabase.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.repository.MovieDatabaseMovieRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MainViewModelTest : TestBase() {
    private val vm = MainViewModel(
        TestPreferencesService(),
        MovieDatabaseConfigurationRepository(TestConfigurationApi()),
        MovieDatabaseMovieRepository(TestMovieApi())
    )

    @Test
    fun popularTest() = runBlocking {
        val popularList = vm.awaitInit().popular.value
        assertNotNull(popularList)
        assertEquals(2, requireNotNull(popularList).size)
    }
}