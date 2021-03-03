package io.viewpoint.moviedatabase.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.extensions.hideSoftInput
import io.viewpoint.moviedatabase.ui.search.databinding.FragmentMovieSearchBinding
import io.viewpoint.moviedatabase.ui.search.viewmodel.MovieSearchViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSearchFragment : Fragment(), RecentSearchKeywordAdapter.Callbacks {
    private lateinit var binding: FragmentMovieSearchBinding

    private val viewModel: MovieSearchViewModel by viewModels()

    private val searchResultAdapter = SearchResultAdapter { binding, result ->
        activity?.run {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                binding.poster,
                binding.poster.transitionName
            )

            startActivity(SearchResultDetailActivity.intent(this, result), options.toBundle())
        }
    }

    private val recentKeywordsAdapter = RecentSearchKeywordAdapter(this)

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
            resultAdapter = searchResultAdapter.withLoadStateHeaderAndFooter(
                header = SearchResultLoadStateAdapter(),
                footer = SearchResultLoadStateAdapter()
            )
            keywordsAdapter = recentKeywordsAdapter

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
        viewModel.results.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                searchResultAdapter.submitData(it)
            }
        }
        viewModel.recentKeywords.observe(viewLifecycleOwner) {
            recentKeywordsAdapter.updateKeywords(it)
        }

        lifecycleScope.launch {
            searchResultAdapter.loadStateFlow
                .collect {
                    viewModel.isLoading.postValue(it.refresh is LoadState.Loading)

                    if (it.refresh is LoadState.Loading) {
                        binding.searchResultList.scrollToPosition(0)
                    }
                }
        }

        // PagingData 에서 데이터 개수를 직접 가져올 수 없는 문제의 workaround
        val searchResultDataObserver = SearchResultCountObserver(searchResultAdapter)
        searchResultAdapter.registerAdapterDataObserver(searchResultDataObserver)
        searchResultDataObserver.currentItemCount.observe(viewLifecycleOwner) { newCount ->
            viewModel.resultCount.value = newCount
        }
    }

    companion object {
        const val TAG = "MovieSearchFragment"
    }

    override fun onRecentKeywordClick(keyword: String) {
        viewModel.keyword.value = keyword
        viewModel.searchCommand()
    }

    override fun onRemoved(keyword: String) {
        viewModel.removeRecentKeyword(keyword)
    }
}