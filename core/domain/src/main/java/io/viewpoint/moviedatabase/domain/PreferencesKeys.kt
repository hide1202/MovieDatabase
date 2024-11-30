package io.viewpoint.moviedatabase.domain

import io.viewpoint.moviedatabase.domain.preferences.PreferenceKey
import io.viewpoint.moviedatabase.domain.preferences.PreferenceKeyWithDefault

object PreferencesKeys {
    val SELECTED_LANGUAGE_ISO = PreferenceKeyWithDefault(
        key = "selectedLanguage",
        type = String::class,
        defaultProvider = {
            Languages.defaultLocale.language
        }
    )

    val SEARCHED_KEYWORDS = PreferenceKey(
        key = "searchedKeywords",
        type = String::class
    )
}