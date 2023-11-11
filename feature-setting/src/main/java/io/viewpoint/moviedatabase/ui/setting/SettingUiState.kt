package io.viewpoint.moviedatabase.ui.setting

import io.viewpoint.moviedatabase.ui.setting.model.Language

internal data class SettingUiState(
    val languages: List<Language> = emptyList(),
    val selectLanguage: Language? = null,
)