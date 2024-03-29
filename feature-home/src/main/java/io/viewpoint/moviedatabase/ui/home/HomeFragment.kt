package io.viewpoint.moviedatabase.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.home.R
import io.viewpoint.moviedatabase.home.databinding.FragmentHomeBinding
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.viewmodel.MainViewModel
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

        val wantToSeeLabelAdapter =
            LabelAdapter(getString(R.string.want_to_see_header), initialVisible = false)
        val wantToSeeAdapter = HomeMovieListAdapter(circle = true, callback = this)
        val popularLabelAdapter = LabelAdapter(
            getString(R.string.popular_header),
            providerClass = PopularMovieListProvider::class
        )
        val popularAdapter = HomeMovieListAdapter(callback = this)
        val nowPlayingLabelAdapter = LabelAdapter(
            getString(R.string.now_playing_header),
            providerClass = NowPlayingMovieListProvider::class
        )
        val nowPlayingAdapter = HomeMovieListAdapter(callback = this)
        val upcomingLabelAdapter = LabelAdapter(
            getString(R.string.upcoming_header),
            providerClass = UpcomingMovieListProvider::class
        )
        val upcomingAdapter = HomeMovieListAdapter(callback = this)
        val topRatedLabelAdapter = LabelAdapter(
            getString(R.string.top_rated_header),
            providerClass = TopRatedMovieListProvider::class
        )
        val topRatedAdapter = HomeMovieListAdapter(callback = this)
        val concatAdapter = ConcatAdapter(
            wantToSeeLabelAdapter,
            MovieListAdapter(wantToSeeAdapter),
            popularLabelAdapter,
            MovieListAdapter(popularAdapter),
            nowPlayingLabelAdapter,
            MovieListAdapter(nowPlayingAdapter),
            upcomingLabelAdapter,
            MovieListAdapter(upcomingAdapter),
            topRatedLabelAdapter,
            MovieListAdapter(topRatedAdapter)
        )

        binding.homeSectionList.adapter = concatAdapter
        viewModel.wantToSee.observeSectionAdapter(wantToSeeLabelAdapter, wantToSeeAdapter)
        viewModel.popular.observeSectionAdapter(popularLabelAdapter, popularAdapter)
        viewModel.nowPlaying.observeSectionAdapter(nowPlayingLabelAdapter, nowPlayingAdapter)
        viewModel.upcoming.observeSectionAdapter(upcomingLabelAdapter, upcomingAdapter)
        viewModel.topRated.observeSectionAdapter(topRatedLabelAdapter, topRatedAdapter)

        binding.refreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.loadData()
            }
        }
    }

    private fun LiveData<List<SearchResultModel>>.observeSectionAdapter(
        labelAdapter: LabelAdapter,
        dataAdapter: HomeMovieListAdapter
    ) {
        this.observe(viewLifecycleOwner) {
            labelAdapter.isVisible = it.isNotEmpty()
            dataAdapter.updateResults(it)
        }
    }

    override fun onMovieClicked(movie: SearchResultModel) {
        val activity = activity ?: return

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("viewpoint://tmdb/movies/detail")
        ).apply {
            // TODO don't use constant name
            putExtra("resultModel", movie)
        }
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(intent)
        }
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}