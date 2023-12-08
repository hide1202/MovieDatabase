package io.viewpoint.moviedatabase.platform.ui.main

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.viewpoint.moviedatabase.ui.common.MovieListActivity
import io.viewpoint.moviedatabase.ui.home.HomeRoute
import io.viewpoint.moviedatabase.ui.search.MovieSearchRoute
import io.viewpoint.moviedatabase.ui.search.SearchResultDetailActivity
import io.viewpoint.moviedatabase.ui.setting.SettingRoute
import io.viewpoint.moviedatabase.ui.setting.SettingViewModel
import io.viewpoint.moviedatabase.viewmodel.MainViewModel
import io.viewpoint.moviedatabase.viewmodel.MovieSearchViewModel

@Composable
fun MovieDatabaseRoute(
    navController: NavHostController = rememberNavController(),
    homeViewModel: MainViewModel = hiltViewModel(),
    searchViewModel: MovieSearchViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
    ) {
        NavHost(
            modifier = Modifier.weight(1f),
            navController = navController,
            startDestination = MainTab.HOME.tag,
        ) {
            composable(MainTab.HOME.tag) {
                HomeRoute(
                    viewModel = homeViewModel,
                    onMoreClicked = { section ->
                        val providerClass = section.providerClass ?: return@HomeRoute
                        context.startActivity(MovieListActivity.intent(context, providerClass))
                    },
                    onMovieClicked = { movie ->
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
                    },
                )
            }

            composable(MainTab.MOVIE_SEARCH.tag) {
                MovieSearchRoute(
                    viewModel = searchViewModel,
                    onSearchResultClick = {
                        context.startActivity(SearchResultDetailActivity.intent(context, it))
                    },
                )
            }

            composable(MainTab.SETTING.tag) {
                SettingRoute(
                    viewModel = settingViewModel,
                )
            }
        }

        MovieDatabaseNavigation(
            selectedItem = selectedTab,
            onSelected = onTabSelected,
        )
    }
}