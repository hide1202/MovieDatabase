package io.viewpoint.moviedatabase.platform.ui.main

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
import io.viewpoint.moviedatabase.model.ui.PopularResultModel
import io.viewpoint.moviedatabase.util.PreferencesKeys
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val preferences: PreferencesService,
    configurationRepository: ConfigurationRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val mapper = PopularResultMapper(configurationRepository)
    private val _popular = MutableLiveData<List<PopularResultModel>>()

    init {
        viewModelScope.launch {
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
        }
    }

    val popular: LiveData<List<PopularResultModel>>
        get() = _popular
}