package io.viewpoint.moviedatabase.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.ui.common.MovieListActivity
import io.viewpoint.moviedatabase.viewmodel.MainViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(inflater.context).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MovieDatabaseTheme {
                    HomeScreen(
                        wantToSeeList = uiState.wantToSeeList,
                        popularList = uiState.popularList,
                        nowPlayingList = uiState.nowPlayingList,
                        upcomingList = uiState.upcomingList,
                        topRatedList = uiState.topRatedList,
                        onMoreClicked = ::onMoreClicked,
                        onMovieClicked = ::onMovieClicked,
                    )
                }
            }
        }
    }

    private fun onMoreClicked(section: Section) {
        val context = context ?: return
        val providerClass = section.providerClass ?: return
        context.startActivity(MovieListActivity.intent(context, providerClass))
    }

    private fun onMovieClicked(movie: SearchResultModel) {
        val context = context ?: return
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("viewpoint://tmdb/movies/detail")
        ).apply {
            // TODO don't use constant name
            putExtra("resultModel", movie)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}