package io.viewpoint.moviedatabase.platform.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.databinding.ActivityMainBinding
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.platform.ui.search.MovieSearchActivity
import io.viewpoint.moviedatabase.util.PreferencesKeys
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    @Inject
    internal lateinit var preferences: SharedPreferences

    @Inject
    internal lateinit var configurationRepository: ConfigurationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val languages = configurationRepository.getSupportedLanguages()
            val adapter = ArrayAdapter(this@MainActivity,
                R.layout.item_language
                , languages
                    .asSequence()
                    .map { it.name }
                    .filter { it.isNotEmpty() }
                    .toList()
            )

            binding.languageSelect.setAdapter(adapter)

            preferences.getString(PreferencesKeys.KEY_SELECTED_LANGUAGE_ISO, null)
                ?.let { savedLanguageIso ->
                    languages.firstOrNull { it.iso_639_1 == savedLanguageIso }
                }
                ?.let {
                    binding.languageSelect.setText(it.name, false)
                }
        }

        binding.languageSelect.addTextChangedListener { editable ->
            editable ?: return@addTextChangedListener
            lifecycleScope.launch {
                val languages = configurationRepository.getSupportedLanguages()
                languages.firstOrNull {
                    it.name == editable.toString()
                }?.let {
                    MovieDatabaseApi.language = it.iso_639_1
                }

                preferences.edit {
                    putString(PreferencesKeys.KEY_SELECTED_LANGUAGE_ISO, MovieDatabaseApi.language)
                }
            }
        }

        binding.searchButton.setOnClickListener {
            startActivity(MovieSearchActivity.intent(this))
        }
    }
}

