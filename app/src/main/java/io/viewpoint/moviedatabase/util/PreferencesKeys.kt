package io.viewpoint.moviedatabase.util

import io.viewpoint.moviedatabase.domain.preferences.PreferenceKey

enum class PreferencesKeys(override val key: String) : PreferenceKey {
    SELECTED_LANGUAGE_ISO("selectedLanguage")
}