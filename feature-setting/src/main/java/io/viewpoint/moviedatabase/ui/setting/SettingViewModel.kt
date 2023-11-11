package io.viewpoint.moviedatabase.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.ui.setting.model.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingViewModel @Inject constructor(
    private val preferences: PreferencesService,
    private val configurationRepository: ConfigurationRepository,
    private val languageMapper: LanguageMapper,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    private fun loadSavedLanguage() {
        viewModelScope.launch {
            val languages = configurationRepository
                .getSupportedLanguages()
                .map {
                    languageMapper.map(it)
                }

            val savedLanguageIso =
                preferences.getValueWithDefault(PreferencesKeys.SELECTED_LANGUAGE_ISO)

            val selectedLanguage = languages.firstOrNull {
                it.languageCode == savedLanguageIso
            }

            _uiState.update { prev ->
                prev.copy(
                    languages = languages,
                    selectLanguage = selectedLanguage,
                )
            }
        }
    }

    fun onLanguageSelected(language: Language) {
        viewModelScope.launch {
            MovieDatabaseApi.language = language.languageCode

            preferences.putValue(
                PreferencesKeys.SELECTED_LANGUAGE_ISO,
                MovieDatabaseApi.language
            )

            _uiState.update { prev ->
                prev.copy(selectLanguage = language)
            }
        }
    }

    fun clearLanguage() {
        viewModelScope.launch {
            preferences.putValue(PreferencesKeys.SELECTED_LANGUAGE_ISO, null)
            MovieDatabaseApi.language =
                preferences.getValueWithDefault(PreferencesKeys.SELECTED_LANGUAGE_ISO)
            loadSavedLanguage()
        }
    }

    init {
        loadSavedLanguage()
    }
}