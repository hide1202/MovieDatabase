package io.viewpoint.moviedatabase.platform.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ActivityMovieSearchBinding
import io.viewpoint.moviedatabase.platform.externsion.hideSoftInput
import io.viewpoint.moviedatabase.platform.externsion.intentToActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSearchActivity : AppCompatActivity() {
    private val binding: ActivityMovieSearchBinding by lazy {
        DataBindingUtil.setContentView<ActivityMovieSearchBinding>(
            this,
            R.layout.activity_movie_search
        )
    }

    private val viewModel: MovieSearchViewModel by viewModels()

    private val searchResultAdapter = SearchResultAdapter { binding, result ->
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            binding.poster,
            binding.poster.transitionName
        )
        startActivity(SearchResultDetailActivity.intent(this, result), options.toBundle())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@MovieSearchActivity
            vm = viewModel
            adapter = searchResultAdapter.withLoadStateHeaderAndFooter(
                header = SearchResultLoadStateAdapter(),
                footer = SearchResultLoadStateAdapter()
            )

            searchInput.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    viewModel.searchCommand.action()
                    true
                } else {
                    false
                }
            }
        }

        viewModel.beforeSearchCommand = {
            this@MovieSearchActivity.hideSoftInput(binding.searchInput)
        }
        viewModel.results.observe(this, Observer {
            lifecycleScope.launch {
                searchResultAdapter.submitData(it)
            }
        })

        lifecycleScope.launch {
            searchResultAdapter.loadStateFlow
                .collect {
                    viewModel.isLoading.postValue(it.refresh is LoadState.Loading)
                }

            @OptIn(ExperimentalPagingApi::class)
            searchResultAdapter.dataRefreshFlow
                .collect {
                    binding.searchResultList.scrollToPosition(0)
                }
        }
    }

    companion object {
        fun intent(context: Context): Intent =
            intentToActivity<MovieSearchActivity>(context)
    }
}
