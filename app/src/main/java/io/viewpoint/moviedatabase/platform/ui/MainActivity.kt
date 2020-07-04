package io.viewpoint.moviedatabase.platform.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ActivityMainBinding
import io.viewpoint.moviedatabase.platform.ui.search.MovieSearchActivity
import io.viewpoint.moviedatabase.platform.ui.setting.SettingActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.setting.setOnClickListener {
            startActivity(SettingActivity.intent(this))
        }

        binding.search.setOnClickListener {
            startActivity(MovieSearchActivity.intent(this))
        }
    }
}

