package io.viewpoint.moviedatabase.platform.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.viewpoint.moviedatabase.BuildConfig
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val api: MovieDatabaseApi by lazy {
        MovieDatabaseApi
            .Builder()
            .apply {
                apiKey = BuildConfig.API_KEY
                if (BuildConfig.DEBUG) {
                    debugLog = {
                        Timber.d(it)
                    }
                }
            }
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.searchButton.setOnClickListener {
            startActivity(MovieSearchActivity.intent(this))
        }
    }
}