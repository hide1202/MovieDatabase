package io.viewpoint.moviedatabase.platform.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.FragmentMovieSearchBinding
import io.viewpoint.moviedatabase.platform.externsion.hideSoftInput
import io.viewpoint.moviedatabase.viewmodel.search.MovieSearchViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSearchFragment : Fragment() {
    private lateinit var binding: FragmentMovieSearchBinding

    private val viewModel: MovieSearchViewModel by viewModels()

    private val searchResultAdapter = SearchResultAdapter { binding, result ->
//        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//            this,
//            binding.poster,
//            binding.poster.transitionName
//        )
//        startActivity(SearchResultDetailActivity.intent(this, result), options.toBundle())

        context?.run {
            startActivity(SearchResultDetailActivity.intent(this, result))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_search,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = this@MovieSearchFragment.viewLifecycleOwner
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
            context?.hideSoftInput(binding.searchInput)
        }
        viewModel.results.observe(viewLifecycleOwner, Observer {
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
}