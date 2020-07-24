package io.viewpoint.moviedatabase.viewmodel.search

import io.viewpoint.moviedatabase.TestBase
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseWantToSeeRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapper
import io.viewpoint.moviedatabase.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.mock.TestMovieApi
import io.viewpoint.moviedatabase.mock.TestWantToSeeDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Test

class MovieSearchResultDetailViewModelTest : TestBase() {
    private val mapper =
        SearchResultMapper(MovieDatabaseConfigurationRepository(TestConfigurationApi()))
    private val movieApi = TestMovieApi()
    private val repository = MovieDatabaseWantToSeeRepository(
        movieApi,
        TestWantToSeeDao()
    )
    private val vm = MovieSearchResultDetailViewModel(repository)

    @Test
    fun invertCommendTest() = runBlocking {
        val popular = movieApi.getPopular().suspended()
        val result = popular.results[0]

        vm.loadWithResult(mapper.map(result))

        val previous = vm.wantToSee.value
        vm.invertWantToSeeCommand()
        assertNotEquals(previous, vm.wantToSee.value)
    }
}