@file:OptIn(ExperimentalComposeUiApi::class)

package io.viewpoint.moviedatabase.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.viewmodel.MovieSearchViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSearchFragment : Fragment() {
    private val viewModel: MovieSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(inflater.context).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()

                val keyword by viewModel.keyword.collectAsState()
                val results = viewModel.results.collectAsLazyPagingItems()
                val recentKeywords by viewModel.recentKeywords.collectAsState()

                val imeController = LocalSoftwareKeyboardController.current
                LaunchedEffect(Unit) {
                    viewModel.beforeSearchCommand = {
                        imeController?.hide()
                    }
                }

                MovieDatabaseTheme {
                    MovieSearchScreen(
                        searchKeyword = keyword,
                        searchResult = results,
                        recentKeywords = recentKeywords,
                        onSearchKeywordChanged = {
                            viewModel.onKeywordChanged(it)
                        },
                        onSearchClick = {
                            viewModel.searchCommand.invoke()
                        },
                        onSearchKeywordCleared = {
                            viewModel.clearSearchKeyword()
                        },
                        onRecentKeywordClick = {
                            viewModel.onKeywordChanged(it)
                            viewModel.searchCommand.invoke()
                        },
                        onRecentKeywordRemoveClick = {
                            coroutineScope.launch {
                                viewModel.removeRecentKeyword(it)
                            }
                        },
                        onSearchResultClick = {
                            startActivity(SearchResultDetailActivity.intent(context, it))
                        },
                    )
                }
            }
        }
    }

    companion object {
        const val TAG = "MovieSearchFragment"
    }
}