package io.viewpoint.moviedatabase.platform.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
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

            val darkTheme = isSystemInDarkTheme()
            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(Unit) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { darkTheme },
                )
                onDispose {}
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

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
