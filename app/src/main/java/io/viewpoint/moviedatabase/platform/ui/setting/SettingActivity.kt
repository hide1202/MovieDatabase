package io.viewpoint.moviedatabase.platform.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.databinding.ActivitySettingBinding
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.platform.externsion.intentToActivity
import io.viewpoint.moviedatabase.util.PreferencesKeys
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivitySettingBinding>(this, R.layout.activity_setting)
    }

    @Inject
    internal lateinit var preferences: PreferencesService

    @Inject
    internal lateinit var configurationRepository: ConfigurationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val languages = configurationRepository.getSupportedLanguages()
            val adapter = ArrayAdapter(this@SettingActivity,
                R.layout.item_language
                , languages
                    .asSequence()
                    .map { it.name }
                    .filter { it.isNotEmpty() }
                    .toList()
            )

            binding.languageSelect.setAdapter(adapter)

            preferences.getString(PreferencesKeys.SELECTED_LANGUAGE_ISO)
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

                preferences.putString(
                    PreferencesKeys.SELECTED_LANGUAGE_ISO,
                    MovieDatabaseApi.language
                )
            }
        }

        binding.close.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun intent(context: Context): Intent =
            intentToActivity<SettingActivity>(context)
    }
}