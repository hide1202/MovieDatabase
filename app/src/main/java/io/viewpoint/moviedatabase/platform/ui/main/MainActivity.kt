package io.viewpoint.moviedatabase.platform.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.ui.common.MovieListProvider
import io.viewpoint.moviedatabase.ui.common.MovieListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var services: Set<@JvmSuppressWildcards MovieListProvider>

    @Inject
    internal lateinit var movieListViewModelFactory: MovieListViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navHostController = rememberNavController()
            var selectedItem by remember {
                mutableStateOf(MainTab.HOME)
            }

            LaunchedEffect(Unit) {
                navHostController.addOnDestinationChangedListener { _, dest, _ ->
                    val route = dest.route ?: return@addOnDestinationChangedListener
                    val tab = MainTab.findTag(route) ?: return@addOnDestinationChangedListener
                    if (selectedItem != tab) {
                        selectedItem = tab
                    }
                }
            }

            MovieDatabaseTheme {
                MovieDatabaseRoute(
                    navController = navHostController,
                    movieListViewModelFactory = movieListViewModelFactory,
                    movieListProviders = services,
                    selectedTab = selectedItem,
                    onTabSelected = {
                        selectedItem = it

                        navHostController.navigate(it.tag)
                    },
                )
            }
        }
    }

    companion object {
        private const val KEY_SELECTED_TAB = "selectedTab"
    }
}
