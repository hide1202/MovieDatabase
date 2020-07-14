package io.viewpoint.moviedatabase.viewmodel.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.domain.popular.PopularResultMapper
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieRepository
import io.viewpoint.moviedatabase.model.ui.HomeMovieListResultModel
import io.viewpoint.moviedatabase.util.PreferencesKeys
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val preferences: PreferencesService,
    configurationRepository: ConfigurationRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val mapper = PopularResultMapper(configurationRepository)
    private val _popular = MutableLiveData<List<HomeMovieListResultModel>>()
    private val _nowPlaying = MutableLiveData<List<HomeMovieListResultModel>>()
    private val _upcoming = MutableLiveData<List<HomeMovieListResultModel>>()
    private val _topRated = MutableLiveData<List<HomeMovieListResultModel>>()

    private val initJob: Job

    init {
        initJob = viewModelScope.launch {
            preferences.getString(PreferencesKeys.SELECTED_LANGUAGE_ISO)
                ?.let { savedSelectedLanguageIso ->
                    val languages = configurationRepository.getSupportedLanguages()
                    languages.firstOrNull {
                        it.iso_639_1 == savedSelectedLanguageIso
                    }
                }
                ?.let {
                    MovieDatabaseApi.language = it.iso_639_1
                }

            _popular.postValue(movieRepository.getPopular()
                .map {
                    mapper.map(it)
                })

            _nowPlaying.postValue(movieRepository.getNowPlayings()
                .map {
                    mapper.map(it)
                })

            _upcoming.postValue(movieRepository.getUpcoming()
                .map {
                    mapper.map(it)
                })

            _topRated.postValue(movieRepository.getTopRated()
                .map {
                    mapper.map(it)
                })
        }
    }

    val popular: LiveData<List<HomeMovieListResultModel>>
        get() = _popular

    val nowPlaying: LiveData<List<HomeMovieListResultModel>>
        get() = _nowPlaying

    val upcoming: LiveData<List<HomeMovieListResultModel>>
        get() = _upcoming

    val topRated: LiveData<List<HomeMovieListResultModel>>
        get() = _topRated

    suspend fun awaitInit(): MainViewModel = apply {
        initJob.join()
    }
}