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

    override fun getInt(key: PreferenceKey, default: Int): Int =
        preferences.getInt(key.key, default)

    override fun getString(key: PreferenceKey): String? =
        preferences.getString(key.key, null)

    override fun putInt(key: PreferenceKey, value: Int) =
        preferences.edit {
            putInt(key.key, value)
        }

    override fun putString(key: PreferenceKey, value: String?) {
        value?.let {
            preferences.edit {
                putString(key.key, it)
            }
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "io.viewpoint.moviedatabase.prefs"
    }
}