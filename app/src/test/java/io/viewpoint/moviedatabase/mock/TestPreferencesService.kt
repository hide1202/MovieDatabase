package io.viewpoint.moviedatabase.mock

import io.viewpoint.moviedatabase.domain.preferences.PreferenceKey
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService

class TestPreferencesService :
    PreferencesService {
    private val preferences = mutableMapOf<String, Any>()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getValue(key: PreferenceKey<T>, defaultValue: T?): T? {
        return (preferences[key.key] as? T) ?: defaultValue
    }

    override fun <T : Any> getValue(key: PreferenceKey<T>, converter: (String) -> T): T? =
        (preferences[key.key] as? String)
            ?.let(converter)

    override fun <T : Any> putValue(key: PreferenceKey<T>, value: T?) {
        value?.let {
            preferences.putIfAbsent(key.key, it)
        }
    }

    override fun <T : Any> putValue(key: PreferenceKey<T>, value: T?, converter: (T) -> String) {
        value?.let {
            preferences.putIfAbsent(key.key, converter(it))
        }
    }

    override fun <T : Any> getValues(key: PreferenceKey<T>, converter: (String) -> T): List<T> =
        (preferences[key.key] as? List<String>)
            ?.map {
                converter(it)
            } ?: emptyList()

    override fun <T : Any> putValues(
        key: PreferenceKey<T>,
        value: List<T>,
        converter: (T) -> String
    ) {
        preferences.putIfAbsent(key.key, value.map(converter))
    }

}