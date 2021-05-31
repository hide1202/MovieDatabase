package io.viewpoint.moviedatabase.viewmodel.search

import io.viewpoint.moviedatabase.domain.CreditModelMapper
import io.viewpoint.moviedatabase.domain.KeywordModelMapper
import io.viewpoint.moviedatabase.domain.WatchProviderModelMapper
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseMovieDetailRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseWantToSeeRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapperProvider
import io.viewpoint.moviedatabase.test.TestBase
import io.viewpoint.moviedatabase.test.mock.TestConfigurationApi
import io.viewpoint.moviedatabase.test.mock.TestMovieApi
import io.viewpoint.moviedatabase.test.mock.TestMovieDetailApi
import io.viewpoint.moviedatabase.test.mock.TestWantToSeeDao
import io.viewpoint.moviedatabase.ui.search.viewmodel.MovieSearchResultDetailViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.*

class MovieSearchResultDetailViewModelTest : TestBase() {
    private val configurationRepository =
        MovieDatabaseConfigurationRepository(TestConfigurationApi())
    private val mapperProvider =
        SearchResultMapperProvider(configurationRepository)

    private val movieApi = TestMovieApi()
    private val movieDetailApi = TestMovieDetailApi()
    private val repository = MovieDatabaseWantToSeeRepository(
        movieDetailApi,
        TestWantToSeeDao()
    )
    private val movieDetailRepository = MovieDatabaseMovieDetailRepository(movieDetailApi)
    private lateinit var vm: MovieSearchResultDetailViewModel

    @Before
    fun setUp() {
        vm = MovieSearchResultDetailViewModel(
            movieDetailRepository = movieDetailRepository,
            wantToSeeRepository = repository,
            resultMapperProvider = SearchResultMapperProvider(configurationRepository),
            creditModelMapper = CreditModelMapper(configurationRepository),
            keywordModelMapper = KeywordModelMapper(),
            watchProviderModelMapper = WatchProviderModelMapper(configurationRepository)
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
        val popular = movieApi.getPopular(1).suspended()
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
        val keywords = vm.keywords
        val recommendations = vm.recommendations

        expectThat(result).isNotNull()
        expectThat(genres.value).isNotNull()
        expectThat(country.value).isNotNull()
        expectThat(credits.value).isNotNull()
        expectThat(productionCompanies.value).isNotNull()
        expectThat(keywords.value).isNotNull()
        expectThat(recommendations.value).isNotNull()

        expectThat(country.value)
            .isNotNull()
            .and {
                hasSize(1)
                get(0).isEqualTo("US")
            }

        expectThat(credits.value).isNotNull().isNotEmpty()
        expectThat(productionCompanies.value).isNotNull().isNotEmpty()
        expectThat(keywords.value).isNotNull().hasSize(20)
        expectThat(recommendations.value).isNotNull().hasSize(21)
    }
}