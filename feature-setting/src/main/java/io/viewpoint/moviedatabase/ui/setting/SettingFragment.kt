package io.viewpoint.moviedatabase.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.ui.setting.databinding.FragmentSettingBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    @Inject
    internal lateinit var preferences: PreferencesService

    @Inject
    internal lateinit var configurationRepository: ConfigurationRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setting,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val languages = configurationRepository.getSupportedLanguages()
            val adapter = ArrayAdapter(requireContext(),
                R.layout.item_language, languages
                    .asSequence()
                    .map { it.name }
                    .filter { it.isNotEmpty() }
                    .toList()
            )

            binding.languageSelect.setAdapter(adapter)

            preferences.getValue(PreferencesKeys.SELECTED_LANGUAGE_ISO)
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

                preferences.putValue(
                    PreferencesKeys.SELECTED_LANGUAGE_ISO,
                    MovieDatabaseApi.language
                )
            }
        }
    }

    companion object {
        const val TAG = "SettingFragment"
    }
}