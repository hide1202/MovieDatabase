package io.viewpoint.moviedatabase.domain.preferences

interface PreferencesService {
    fun getInt(key: PreferenceKey, default: Int): Int

    fun getString(key: PreferenceKey, default: String): String =
        getString(key) ?: default

    fun getString(key: PreferenceKey): String?

    fun putInt(key: PreferenceKey, value: Int)

    fun putString(key: PreferenceKey, value: String?)
}