package io.viewpoint.moviedatabase.mock

import io.viewpoint.moviedatabase.domain.preferences.PreferenceKey
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService

class TestPreferencesService :
    PreferencesService {
    private val preferences = mutableMapOf<String, Any>()

    override fun getInt(key: PreferenceKey, default: Int): Int = preferences[key.key] as Int

    override fun getString(key: PreferenceKey): String? = preferences[key.key] as? String

    override fun putInt(key: PreferenceKey, value: Int) {
        preferences[key.key] = value
    }

    override fun putString(key: PreferenceKey, value: String?) {
        if (value != null) {
            preferences[key.key] = value
        } else {
            preferences.remove(key.key)
        }
    }

}