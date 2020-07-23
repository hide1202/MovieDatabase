package io.viewpoint.moviedatabase.platform.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.FragmentHomeBinding
import io.viewpoint.moviedatabase.viewmodel.main.MainViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wantToSeeAdapter = HomeMovieListAdapter(circle = true)
        val popularAdapter = HomeMovieListAdapter()
        val nowPlayingAdapter = HomeMovieListAdapter()
        val upcomingAdapter = HomeMovieListAdapter()
        val topRatedAdapter = HomeMovieListAdapter()
        binding.popularList.adapter = ConcatAdapter(
            LabelAdapter(getString(R.string.want_to_see_header)),
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
        viewModel.wantToSee.observe(viewLifecycleOwner, Observer {
            wantToSeeAdapter.updateResults(it)
        })
        viewModel.popular.observe(viewLifecycleOwner, Observer {
            popularAdapter.updateResults(it)
        })
        viewModel.nowPlaying.observe(viewLifecycleOwner, Observer {
            nowPlayingAdapter.updateResults(it)
        })
        viewModel.upcoming.observe(viewLifecycleOwner, Observer {
            upcomingAdapter.updateResults(it)
        })
        viewModel.topRated.observe(viewLifecycleOwner, Observer {
            topRatedAdapter.updateResults(it)
        })
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}