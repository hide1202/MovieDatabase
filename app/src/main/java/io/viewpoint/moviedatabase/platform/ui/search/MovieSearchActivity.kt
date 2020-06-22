package io.viewpoint.moviedatabase.platform.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import arrow.core.getOrHandle
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ActivityMovieSearchBinding
import io.viewpoint.moviedatabase.platform.externsion.hideSoftInput
import kotlinx.coroutines.launch

class MovieSearchActivity : AppCompatActivity() {
    private val binding: ActivityMovieSearchBinding by lazy {
        DataBindingUtil.setContentView<ActivityMovieSearchBinding>(
            this,
            R.layout.activity_movie_search
        )
    }

    private val viewModel: MovieSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        val adapter = SearchResultAdapter()
        binding.searchResultList.adapter = adapter

        binding.searchButton.setOnClickListener {
            val keyword = binding.searchInput.text?.toString() ?: return@setOnClickListener

            this@MovieSearchActivity.hideSoftInput(binding.searchInput)
            lifecycleScope.launch {
                val resultEither = viewModel.searchMovies(keyword)

                val searchResults = resultEither
                    .getOrHandle {
                        emptyList()
                    }
                adapter.updateResults(searchResults)
            }
        }
    }

    companion object {
        fun intent(context: Context): Intent =
            Intent(context, MovieSearchActivity::class.java)
    }
}