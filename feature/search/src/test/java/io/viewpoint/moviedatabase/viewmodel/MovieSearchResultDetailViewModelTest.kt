package io.viewpoint.moviedatabase.viewmodel

import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty
import strikt.assertions.isNotEqualTo
import strikt.assertions.isNotNull

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
            watchProviderModelMapper = WatchProviderModelMapper(configurationRepository),
            savedStateHandle = SavedStateHandle(mapOf(MovieSearchResultDetailViewModel.EXTRA_MOVIE_ID to 557)),
        )
    }

    @Test
    fun invertCommendWithoutLoadTest(): Unit = runBlocking {
        val before = vm.uiState.value.wantToSee
        vm.invertWantToSeeCommand()
        val after = vm.uiState.value.wantToSee

        expectThat(after).isEqualTo(before)
    }

    @Test
    fun invertCommendTest(): Unit = runBlocking {
        val popular = movieApi.getPopular(1).suspended()
        val result = popular.results[0]

        vm.loadWithResult(mapperProvider.mapperFromMovie.map(result))

        val previous = vm.uiState.value.wantToSee
        vm.invertWantToSeeCommand()
        val firstInvert = vm.uiState.value.wantToSee
        vm.invertWantToSeeCommand()
        val secondInvert = vm.uiState.value.wantToSee

        expectThat(firstInvert).isNotEqualTo(previous)
        expectThat(secondInvert).isNotEqualTo(firstInvert)
    }

    @Test
    fun loadWithMovieIdTest(): Unit = runBlocking {
        val result = vm.loadWithMovieId(movieId = 557)
        val genres = vm.uiState.value.genres
        val country = vm.uiState.value.countries
        val credits = vm.uiState.value.credits
        val productionCompanies = vm.uiState.value.productionCompanies
        val keywords = vm.uiState.value.keywords
        val recommendations = vm.uiState.value.recommendations

        expectThat(result).isNotNull()
        expectThat(genres).isNotEmpty()
        expectThat(country).isNotEmpty()
        expectThat(credits).isNotEmpty()
        expectThat(productionCompanies).isNotEmpty()
        expectThat(keywords).isNotEmpty()
        expectThat(recommendations).isNotEmpty()

        expectThat(country)
            .and {
                hasSize(1)
                get(0).isEqualTo("US")
            }
    }
}