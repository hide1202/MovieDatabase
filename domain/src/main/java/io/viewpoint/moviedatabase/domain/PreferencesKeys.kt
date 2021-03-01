package io.viewpoint.moviedatabase.domain

import io.viewpoint.moviedatabase.domain.preferences.PreferenceKey

object PreferencesKeys {
    val SELECTED_LANGUAGE_ISO = PreferenceKey(
        key = "selectedLanguage",
        type = String::class
    )

    val SEARCHED_KEYWORDS = PreferenceKey(
        key = "searchedKeywords",
        type = String::class
    )
}