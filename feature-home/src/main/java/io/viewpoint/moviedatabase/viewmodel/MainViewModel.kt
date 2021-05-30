package io.viewpoint.moviedatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferences: PreferencesService,
    configurationRepository: ConfigurationRepository,
    private val wantToSeeRepository: WantToSeeRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val mapper = SearchResultMapperProvider(configurationRepository)
    private val _isLoading = MutableLiveData<Boolean>(false)
    private val _wantToSee = MutableLiveData<List<SearchResultModel>>()
    private val _popular = MutableLiveData<List<SearchResultModel>>()
    private val _nowPlaying = MutableLiveData<List<SearchResultModel>>()
    private val _upcoming = MutableLiveData<List<SearchResultModel>>()
    private val _topRated = MutableLiveData<List<SearchResultModel>>()

    private val initJob: Job

    init {
        initJob = viewModelScope.launch {
            MovieDatabaseApi.language =
                preferences.getValueWithDefault(PreferencesKeys.SELECTED_LANGUAGE_ISO)

            loadData()
        }
    }

    suspend fun loadData() {
        _isLoading.value = true

        _wantToSee.postValue(wantToSeeRepository.getWantToSeeMovies()
            .effectMap { list ->
                list.map {
                    mapper.mapperFromMovieDetail.map(it)
                }
            }
            .attempt()
            .suspended()
            .getOrElse { emptyList() })

        _popular.postValue(movieRepository.getPopular()
            .map {
                mapper.mapperFromMovie.map(it)
            })

        _nowPlaying.postValue(movieRepository.getNowPlayings()
            .map {
                mapper.mapperFromMovie.map(it)
            })

        _upcoming.postValue(movieRepository.getUpcoming()
            .map {
                mapper.mapperFromMovie.map(it)
            })

        _topRated.postValue(movieRepository.getTopRated()
            .map {
                mapper.mapperFromMovie.map(it)
            })

        _isLoading.value = false
    }

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val wantToSee: LiveData<List<SearchResultModel>>
        get() = _wantToSee

    val popular: LiveData<List<SearchResultModel>>
        get() = _popular

    val nowPlaying: LiveData<List<SearchResultModel>>
        get() = _nowPlaying

    val upcoming: LiveData<List<SearchResultModel>>
        get() = _upcoming

    val topRated: LiveData<List<SearchResultModel>>
        get() = _topRated

    suspend fun awaitInit(): MainViewModel = apply {
        initJob.join()
    }
}