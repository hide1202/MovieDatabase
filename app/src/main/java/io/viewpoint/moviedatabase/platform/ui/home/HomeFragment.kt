package io.viewpoint.moviedatabase.platform.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.FragmentHomeBinding
import io.viewpoint.moviedatabase.platform.ui.search.SearchResultDetailActivity
import io.viewpoint.moviedatabase.viewmodel.main.MainViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeMovieListAdapter.Callback {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wantToSeeLabelAdapter = LabelAdapter(getString(R.string.want_to_see_header))
        val wantToSeeAdapter = HomeMovieListAdapter(circle = true, callback = this)
        val popularAdapter = HomeMovieListAdapter(callback = this)
        val nowPlayingAdapter = HomeMovieListAdapter(callback = this)
        val upcomingAdapter = HomeMovieListAdapter(callback = this)
        val topRatedAdapter = HomeMovieListAdapter(callback = this)
        val concatAdapter = ConcatAdapter(
            wantToSeeLabelAdapter,
            MovieListAdapter(wantToSeeAdapter),
            LabelAdapter(getString(R.string.popular_header)),
            MovieListAdapter(popularAdapter),
            LabelAdapter(getString(R.string.now_playing_header)),
            MovieListAdapter(nowPlayingAdapter),
            LabelAdapter(getString(R.string.upcoming_header)),
            MovieListAdapter(upcomingAdapter),
            LabelAdapter(getString(R.string.top_rated_header)),
            MovieListAdapter(topRatedAdapter)
        )

        binding.homeSectionList.adapter = concatAdapter
        viewModel.wantToSee.observe(viewLifecycleOwner) {
            wantToSeeLabelAdapter.updateIsEmpty(it.isEmpty())
            wantToSeeAdapter.updateResults(it)
        }
        viewModel.popular.observe(viewLifecycleOwner) {
            popularAdapter.updateResults(it)
        }
        viewModel.nowPlaying.observe(viewLifecycleOwner) {
            nowPlayingAdapter.updateResults(it)
        }
        viewModel.upcoming.observe(viewLifecycleOwner) {
            upcomingAdapter.updateResults(it)
        }
        viewModel.topRated.observe(viewLifecycleOwner) {
            topRatedAdapter.updateResults(it)
        }

        binding.refreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.loadData()
            }
        }
    }

    override fun onMovieClicked(movieId: Int) {
        val activity = activity ?: return
        activity.startActivity(SearchResultDetailActivity.withMovieId(activity, movieId))
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}