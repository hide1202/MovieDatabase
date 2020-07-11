package io.viewpoint.moviedatabase.platform.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ActivityMainBinding
import io.viewpoint.moviedatabase.platform.externsion.dp
import io.viewpoint.moviedatabase.platform.ui.search.MovieSearchActivity
import io.viewpoint.moviedatabase.platform.ui.setting.SettingActivity
import io.viewpoint.moviedatabase.platform.util.SpaceItemDecoration
import io.viewpoint.moviedatabase.viewmodel.main.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val viewModel: MainViewModel by viewModels()

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
        viewModel.popular.observe(this, Observer {
            adapter.updateResults(it)
        })
    }
}
