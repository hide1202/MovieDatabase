package io.viewpoint.moviedatabase.platform.common

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.viewpoint.moviedatabase.domain.preferences.PreferenceKey
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AndroidPreferencesService @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesService {
    private val dataStore = context.dataStore

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    override suspend fun <T : Any> getValue(key: PreferenceKey<T>, defaultValue: T?): T? =
        when (key.type) {
            String::class -> dataStore.data.map { it[stringPreferencesKey(key.key)] }
                .firstOrNull()
            Int::class -> dataStore.data.map { it[intPreferencesKey(key.key)] }
                .firstOrNull()
            else -> throwUnsupportedType(key)
        } as? T?

    override suspend fun <T : Any> getValue(key: PreferenceKey<T>, converter: (String) -> T): T? =
        dataStore.data
            .map {
                it[stringPreferencesKey(key.key)]
            }
            .firstOrNull()
            ?.let(converter)

    override suspend fun <T : Any> putValue(key: PreferenceKey<T>, value: T?) {
        @Suppress("UNCHECKED_CAST")
        val preferenceKey: Preferences.Key<T> = when (key.type) {
            String::class -> stringPreferencesKey(key.key)
            Int::class -> intPreferencesKey(key.key)
            else -> throwUnsupportedType(key)
        } as Preferences.Key<T>

        if (value == null) {
            dataStore.edit {
                it.remove(preferenceKey)
            }
            return
        } else {
            dataStore.edit {
                it[preferenceKey] = value
            }
        }
    }

    override suspend fun <T : Any> putValue(
        key: PreferenceKey<T>,
        value: T?,
        converter: (T) -> String
    ) {
        @Suppress("UNCHECKED_CAST")
        val preferenceKey: Preferences.Key<String> = stringPreferencesKey(key.key)

        if (value == null) {
            dataStore.edit {
                it.remove(preferenceKey)
            }
            return
        } else {
            dataStore.edit {
                it[preferenceKey] = converter(value)
            }
        }
    }

    override suspend fun <T : Any> getValues(
        key: PreferenceKey<T>,
        converter: (String) -> T
    ): List<T> {
        return dataStore.data
            .map {
                it[stringSetPreferencesKey(key.key)]
            }
            .onEach {
                it
            }
            .firstOrNull()
            ?.map(converter)
            ?: emptyList()
    }

    override suspend fun <T : Any> putValues(
        key: PreferenceKey<T>,
        value: List<T>,
        converter: (T) -> String
    ) {
        dataStore.edit {
            it[stringSetPreferencesKey(key.key)] = value.map(converter).toSet()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> preferenceKey(key: PreferenceKey<T>): Preferences.Key<T> {
        return when (key.type) {
            String::class -> stringPreferencesKey(key.key)
            Int::class -> intPreferencesKey(key.key)
            Set::class -> if (key.type.typeParameters[0] == String::class) {
                stringSetPreferencesKey(key.key)
            } else {
                throwUnsupportedType(key)
            }
            else -> throwUnsupportedType(key)
        } as Preferences.Key<T>
    }

    private fun throwUnsupportedType(key: PreferenceKey<*>): Nothing =
        throw UnsupportedOperationException(
            "unsupported %s type in preferences"
                .format(key.type.simpleName)
        )

    companion object {
        private const val PREFERENCES_NAME = "io.viewpoint.moviedatabase.prefs"

        private val Context.dataStore by preferencesDataStore(
            name = PREFERENCES_NAME,
            produceMigrations = { context ->
                listOf(
                    SharedPreferencesMigration({
                        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                    })
                )
            })
    }
}