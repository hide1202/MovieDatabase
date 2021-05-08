package io.viewpoint.moviedatabase.viewmodel.search

import io.viewpoint.moviedatabase.domain.CreditModelMapper
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseMovieRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseWantToSeeRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapperProvider
import io.viewpoint.moviedatabase.test.TestBase
import io.viewpoint.moviedatabase.test.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.test.mock.TestMovieApi
import io.viewpoint.moviedatabase.test.mock.TestWantToSeeDao
import io.viewpoint.moviedatabase.ui.search.viewmodel.MovieSearchResultDetailViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.*

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
    fun invertCommendWithoutLoadTest(): Unit = runBlocking {
        val before = vm.wantToSee.value
        vm.invertWantToSeeCommand()
        val after = vm.wantToSee.value

        expectThat(before).isNotNull()
        expectThat(after).isNotNull()
        expectThat(after).isEqualTo(before)
    }

    @Test
    fun invertCommendTest(): Unit = runBlocking {
        val popular = movieApi.getPopular().suspended()
        val result = popular.results[0]

        vm.loadWithResult(mapperProvider.mapperFromMovie.map(result))

        val previous = vm.wantToSee.value
        vm.invertWantToSeeCommand()
        val firstInvert = vm.wantToSee.value
        vm.invertWantToSeeCommand()
        val secondInvert = vm.wantToSee.value

        expectThat(firstInvert).isNotEqualTo(previous)
        expectThat(secondInvert).isNotEqualTo(firstInvert)
    }

    @Test
    fun loadWithMovieIdTest(): Unit = runBlocking {
        val result = vm.loadWithMovieId(movieId = 557)
        val genres = vm.genres
        val country = vm.countries
        val credits = vm.credits
        val productionCompanies = vm.productionCompanies

        expectThat(result).isNotNull()
        expectThat(genres.value).isNotNull()
        expectThat(country.value).isNotNull()
        expectThat(credits.value).isNotNull()
        expectThat(productionCompanies.value).isNotNull()

        expectThat(country.value)
            .isNotNull()
            .and {
                hasSize(1)
                get(0).isEqualTo("US")
            }

        expectThat(credits.value).isNotNull().isNotEmpty()
        expectThat(productionCompanies.value).isNotNull().isNotEmpty()
    }
}