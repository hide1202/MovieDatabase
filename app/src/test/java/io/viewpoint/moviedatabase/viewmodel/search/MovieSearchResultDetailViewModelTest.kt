package io.viewpoint.moviedatabase.viewmodel.search

import io.viewpoint.moviedatabase.TestBase
import io.viewpoint.moviedatabase.domain.CreditModelMapper
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseMovieRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseWantToSeeRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapperProvider
import io.viewpoint.moviedatabase.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.mock.TestMovieApi
import io.viewpoint.moviedatabase.mock.TestWantToSeeDao
import io.viewpoint.moviedatabase.ui.search.viewmodel.MovieSearchResultDetailViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MovieSearchResultDetailViewModelTest : TestBase() {
    private val mapperProvider =
        SearchResultMapperProvider(MovieDatabaseConfigurationRepository(TestConfigurationApi()))
    private val creditMapper =
        CreditModelMapper(MovieDatabaseConfigurationRepository(TestConfigurationApi()))
    private val movieApi = TestMovieApi()
    private val repository = MovieDatabaseWantToSeeRepository(
        movieApi,
        TestWantToSeeDao()
    )
    private val movieRepository = MovieDatabaseMovieRepository(movieApi)
    private lateinit var vm: MovieSearchResultDetailViewModel

    @Before
    fun setUp() {
        vm = MovieSearchResultDetailViewModel(
            movieRepository = movieRepository,
            wantToSeeRepository = repository,
            resultMapperProvider = mapperProvider,
            creditModelMapper = creditMapper
        )
    }

    @Test
    fun invertCommendWithoutLoadTest() = runBlocking {
        val before = vm.wantToSee.value
        vm.invertWantToSeeCommand()
        val after = vm.wantToSee.value
        assertNotNull(before)
        assertNotNull(after)
        assertEquals(after, before)
    }

    @Test
    fun invertCommendTest() = runBlocking {
        val popular = movieApi.getPopular().suspended()
        val result = popular.results[0]

        vm.loadWithResult(mapperProvider.mapperFromMovie.map(result))

        val previous = vm.wantToSee.value
        vm.invertWantToSeeCommand()
        val firstInvert = vm.wantToSee.value
        assertNotEquals(previous, firstInvert)
        vm.invertWantToSeeCommand()
        val secondInvert = vm.wantToSee.value
        assertNotEquals(firstInvert, secondInvert)
    }

    @Test
    fun loadWithMovieIdTest() = runBlocking {
        val result = vm.loadWithMovieId(movieId = 557)
        val genres = vm.genres
        val country = vm.countries
        val credits = vm.credits
        val productionCompanies = vm.productionCompanies
        assertNotNull(result)
        assertNotNull(genres.value)
        assertNotNull(country.value)
        assertNotNull(credits.value)
        assertNotNull(productionCompanies.value)
        assertEquals(1, country.value?.size)
        assertEquals("US", country.value?.getOrNull(0))
        assertFalse(credits.value.isNullOrEmpty())
        assertFalse(productionCompanies.value.isNullOrEmpty())
    }
}