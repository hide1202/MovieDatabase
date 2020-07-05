package io.viewpoint.moviedatabase.platform.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.databinding.ActivityMainBinding
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.platform.externsion.dp
import io.viewpoint.moviedatabase.platform.ui.search.MovieSearchActivity
import io.viewpoint.moviedatabase.platform.ui.setting.SettingActivity
import io.viewpoint.moviedatabase.platform.util.SpaceItemDecoration
import io.viewpoint.moviedatabase.util.PreferencesKeys
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val viewModel: MainViewModel by viewModels()

    @Inject
    internal lateinit var preferences: SharedPreferences

    @Inject
    internal lateinit var configurationRepository: ConfigurationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.setting.setOnClickListener {
            startActivity(SettingActivity.intent(this))
        }

        binding.search.setOnClickListener {
            startActivity(MovieSearchActivity.intent(this))
        }

        val adapter = PopularAdapter()
        binding.popularList.adapter = adapter
        binding.popularList.addItemDecoration(
            SpaceItemDecoration(16.dp)
        )

        // for initial loading
        lifecycleScope.launch {
            preferences.getString(PreferencesKeys.KEY_SELECTED_LANGUAGE_ISO, null)
                ?.let { savedSelectedLanguageIso ->
                    val languages = configurationRepository.getSupportedLanguages()
                    languages.firstOrNull {
                        it.iso_639_1 == savedSelectedLanguageIso
                    }
                }
                ?.let {
                    MovieDatabaseApi.language = it.iso_639_1
                }

            adapter.updateResults(viewModel.getPopular())
        }
    }
}
