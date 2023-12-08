package io.viewpoint.moviedatabase.ui.setting

import io.viewpoint.moviedatabase.ui.setting.model.Language

data class SettingUiState(
    val languages: List<Language> = emptyList(),
    val selectLanguage: Language? = null,
)