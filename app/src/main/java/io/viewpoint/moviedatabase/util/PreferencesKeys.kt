package io.viewpoint.moviedatabase.util

import io.viewpoint.moviedatabase.domain.preferences.PreferenceKey

object PreferencesKeys {
    val SELECTED_LANGUAGE_ISO = PreferenceKey(
        key = "selectedLanguage",
        type = String::class
    )
}