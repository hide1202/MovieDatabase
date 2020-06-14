package io.viewpoint.moviedatabase.platform.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import arrow.core.Either
import io.viewpoint.moviedatabase.BuildConfig
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.api.SearchApi
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val api: MovieDatabaseApi by lazy {
        MovieDatabaseApi
            .Builder()
            .apply {
                apiKey = BuildConfig.API_KEY
                if (BuildConfig.DEBUG) {
                    debugLog = {
                        Log.i(TAG, it)
                    }
                }
            }
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            val result = api.get<SearchApi>()
                .searchMovie("spider-man")
                .attempt()
                .suspended()

            when (result) {
                is Either.Right -> Log.i("MainActivity", "result: ${result.b}")
                is Either.Left -> Log.e("MainActivity", "exception: ${result.a}")
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}