package io.viewpoint.moviedatabase.platform.common

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import io.viewpoint.moviedatabase.domain.preferences.PreferenceKey
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import javax.inject.Inject

class AndroidPreferencesService @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesService {
    private val preferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    override fun <T : Any> getValue(key: PreferenceKey<T>, defaultValue: T?): T? =
        when (key.type) {
            String::class -> preferences.getString(key.key, defaultValue as String?)
            Int::class -> preferences.getInt(key.key, defaultValue as? Int ?: 0)
            else -> null
        } as? T?

    override fun <T : Any> putValue(key: PreferenceKey<T>, value: T?) {
        if (value == null) {
            if (preferences.contains(key.key)) {
                preferences.edit {
                    remove(key.key)
                }
            }
            return
        }

        when (key.type) {
            String::class -> preferences.edit {
                putString(key.key, value as String)
            }
            Int::class -> preferences.edit {
                putInt(key.key, value as Int)
            }
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "io.viewpoint.moviedatabase.prefs"
    }
}