package io.viewpoint.moviedatabase.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.getOrElse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.viewpoint.moviedatabase.domain.CreditModelMapper
import io.viewpoint.moviedatabase.domain.KeywordModelMapper
import io.viewpoint.moviedatabase.domain.WatchProviderModelMapper
import io.viewpoint.moviedatabase.domain.repository.MovieDetailRepository
import io.viewpoint.moviedatabase.domain.repository.WantToSeeRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapperProvider
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.model.ui.CreditModel
import io.viewpoint.moviedatabase.model.ui.KeywordModel
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.model.ui.WatchProviderModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

data class MovieDetailUiState(
    val wantToSee: Boolean = false,
    val genres: List<MovieDetail.Genre> = emptyList(),
    val countries: List<String> = emptyList(),
    val credits: List<CreditModel> = emptyList(),
    val productionCompanies: List<SearchResultModel.ProductionCompany> = emptyList(),
    val keywords: List<KeywordModel> = emptyList(),
    val recommendations: List<SearchResultModel> = emptyList(),
    val watchProviders: WatchProviderModel? = null,
)

@HiltViewModel
class MovieSearchResultDetailViewModel @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val wantToSeeRepository: WantToSeeRepository,
    private val resultMapperProvider: SearchResultMapperProvider,
    private val creditModelMapper: CreditModelMapper,
    private val keywordModelMapper: KeywordModelMapper,
    private val watchProviderModelMapper: WatchProviderModelMapper,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _result =
        MutableStateFlow<SearchResultModel?>(savedStateHandle[EXTRA_RESULT_MODEL])
    val result: StateFlow<SearchResultModel?> = _result.asStateFlow()

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    val invertWantToSeeCommand = Command {
        val result = result.value ?: return@Command
        val wantToSee = uiState.value.wantToSee

        viewModelScope.launch {
            val either = if (wantToSee) {
                wantToSeeRepository.removeWantToSeeMovie(result.id)
            } else {
                wantToSeeRepository.addWantToSeeMovie(result.id)
            }.attempt()
                .suspended()

            if (either is Either.Right) {
                _uiState.update { prev ->
                    prev.copy(wantToSee = !wantToSee)
                }
            }
        }
    }

    suspend fun loadWithMovieId(movieId: Int): SearchResultModel? {
        val result = movieDetailRepository.getMovieDetail(movieId)
            ?.let {
                fillDetailData(it)
                resultMapperProvider.mapperFromMovieDetail.map(it)
            }
        if (result != null) {
            loadWithResult(result)
        }
        return result
    }

    suspend fun loadWithResult(result: SearchResultModel) {
        _result.value = result

        val wantToSee = wantToSeeRepository.hasWantToSeeMovie(result.id)
            .attempt()
            .suspended()
            .getOrElse { false }

        _uiState.update { prev ->
            prev.copy(wantToSee = wantToSee)
        }

        if (uiState.value.genres.isEmpty()) {
            movieDetailRepository.getMovieDetail(result.id)
                ?.let {
                    fillDetailData(it)
                }
        }
    }

    private suspend fun fillDetailData(movieDetail: MovieDetail): SearchResultModel {
        val genres = movieDetail.genres
        val countries = movieDetail.production_countries.map { it.iso_3166_1 }

        // TODO call parallel with a move detail
        val credits = movieDetailRepository.getCredits(movieDetail.id)
            .map {
                creditModelMapper.map(it)
            }
        val keywords = movieDetailRepository.getKeywords(movieDetail.id)
            .map {
                keywordModelMapper.map(it)
            }
        val recommendations = movieDetailRepository.getRecommendations(movieDetail.id)
            .map {
                resultMapperProvider.mapperFromMovie.map(it)
            }
        val watchProviders =
            movieDetailRepository.getWatchProviders(movieDetail.id, Locale.getDefault().country)
                ?.let {
                    watchProviderModelMapper.map(it)
                }

        val result = resultMapperProvider.mapperFromMovieDetail
            .map(movieDetail)

        _uiState.update { prev ->
            prev.copy(
                genres = genres,
                countries = countries,
                credits = credits,
                productionCompanies = result.productionCompanies,
                keywords = keywords,
                recommendations = recommendations,
                watchProviders = watchProviders,
            )
        }

        return result
    }

    init {
        val movieId: Int? = savedStateHandle[EXTRA_MOVIE_ID]
        val resultArgument: SearchResultModel? = savedStateHandle[EXTRA_RESULT_MODEL]

        viewModelScope.launch {
            _result.value = if (movieId != null) {
                loadWithMovieId(movieId)
            } else {
                resultArgument?.also {
                    loadWithResult(it)
                } ?: throw IllegalArgumentException()
            }

            Timber.d("result %s", _result.value)
        }
    }

    companion object {
        const val EXTRA_RESULT_MODEL = "resultModel"
        const val EXTRA_MOVIE_ID = "movieId"
    }
}