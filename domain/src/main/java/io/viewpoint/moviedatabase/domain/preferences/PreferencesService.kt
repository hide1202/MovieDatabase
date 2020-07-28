package io.viewpoint.moviedatabase.domain.preferences

interface PreferencesService {
    fun <T : Any> getValue(key: PreferenceKey<T>, defaultValue: T? = null): T?

    fun <T : Any> putValue(key: PreferenceKey<T>, value: T?)
}