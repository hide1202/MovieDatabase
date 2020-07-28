package io.viewpoint.moviedatabase.domain.preferences

interface PreferencesService {
    fun <T : Any> getValue(key: PreferenceKey<T>, defaultValue: T? = null): T?

    fun <T : Any> putValue(key: PreferenceKey<T>, value: T?)

    fun <T : Any> getValues(
        key: PreferenceKey<T>,
        converter: (String) -> T
    ): List<T>

    fun <T : Any> putValues(
        key: PreferenceKey<T>,
        value: List<T>,
        converter: (T) -> String
    )
}