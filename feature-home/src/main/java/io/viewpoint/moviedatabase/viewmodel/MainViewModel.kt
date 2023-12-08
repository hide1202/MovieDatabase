package io.viewpoint.moviedatabase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.getOrElse
import arrow.fx.extensions.io.async.effectMap
import dagger.hilt.android.lifecycle.HiltViewModel
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieRepository
import io.viewpoint.moviedatabase.domain.repository.WantToSeeRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapperProvider
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val wantToSeeList: List<SearchResultModel> = emptyList(),
    val popularList: List<SearchResultModel> = emptyList(),
    val nowPlayingList: List<SearchResultModel> = emptyList(),
    val upcomingList: List<SearchResultModel> = emptyList(),
    val topRatedList: List<SearchResultModel> = emptyList(),
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferences: PreferencesService,
    configurationRepository: ConfigurationRepository,
    private val wantToSeeRepository: WantToSeeRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val mapper = SearchResultMapperProvider(configurationRepository)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val initJob: Job

    init {
        initJob = viewModelScope.launch {
            MovieDatabaseApi.language =
                preferences.getValueWithDefault(PreferencesKeys.SELECTED_LANGUAGE_ISO)

            loadData()
        }
    }

    suspend fun loadData() = coroutineScope {
        val wantToSeeDeferred = async {
            wantToSeeRepository.getWantToSeeMovies()
                .effectMap { list ->
                    list.map {
                        mapper.mapperFromMovieDetail.map(it)
                    }
                }
                .attempt()
                .suspended()
                .getOrElse { emptyList() }
        }

        val popularDeferred = async {
            movieRepository.getPopular()
                .map {
                    mapper.mapperFromMovie.map(it)
                }
        }

        val nowPlayingDeferred = async {
            movieRepository.getNowPlayings()
                .map {
                    mapper.mapperFromMovie.map(it)
                }
        }

        val upcomingDeferred = async {
            movieRepository.getUpcoming()
                .map {
                    mapper.mapperFromMovie.map(it)
                }
        }

        val topRatedDeferred = async {
            movieRepository.getTopRated()
                .map {
                    mapper.mapperFromMovie.map(it)
                }
        }

        val wantToSee = wantToSeeDeferred.await()
        val popular = popularDeferred.await()
        val nowPlaying = nowPlayingDeferred.await()
        val upcoming = upcomingDeferred.await()
        val topRated = topRatedDeferred.await()

        _uiState.update { prev ->
            prev.copy(
                wantToSeeList = wantToSee,
                popularList = popular,
                nowPlayingList = nowPlaying,
                upcomingList = upcoming,
                topRatedList = topRated,
            )
        }
    }

    suspend fun awaitInit(): MainViewModel = apply {
        initJob.join()
    }
}