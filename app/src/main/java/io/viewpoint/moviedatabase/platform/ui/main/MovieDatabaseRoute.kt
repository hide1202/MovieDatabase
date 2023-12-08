package io.viewpoint.moviedatabase.platform.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.viewpoint.moviedatabase.ui.common.MovieListProvider
import io.viewpoint.moviedatabase.ui.common.MovieListScreen
import io.viewpoint.moviedatabase.ui.common.MovieListViewModel
import io.viewpoint.moviedatabase.ui.home.HomeRoute
import io.viewpoint.moviedatabase.ui.search.MovieDetailRoute
import io.viewpoint.moviedatabase.ui.search.MovieSearchRoute
import io.viewpoint.moviedatabase.ui.setting.SettingRoute
import io.viewpoint.moviedatabase.ui.setting.SettingViewModel
import io.viewpoint.moviedatabase.viewmodel.MainViewModel
import io.viewpoint.moviedatabase.viewmodel.MovieSearchResultDetailViewModel
import io.viewpoint.moviedatabase.viewmodel.MovieSearchViewModel
import kotlin.reflect.KClass

private const val movieDetailRoute = "movies/{movieId}"
fun movieDetailRoute(movieId: Int) = "movies/$movieId"

private const val movieListProviderArgument = "provider"
private const val movieListRoute = "movieList/{$movieListProviderArgument}"
fun movieListRoute(provider: KClass<out MovieListProvider>) = "movieList/${provider.qualifiedName}"

@Composable
fun MovieDatabaseRoute(
    navController: NavHostController = rememberNavController(),
    homeViewModel: MainViewModel = hiltViewModel(),
    searchViewModel: MovieSearchViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    movieListViewModelFactory: MovieListViewModel.Factory,
    movieListProviders: Set<MovieListProvider>,
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit
) {
    Column(
        modifier = Modifier
            .systemBarsPadding()
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
                        navController.navigate(movieListRoute(providerClass))
                    },
                    onMovieClicked = { movie ->
                        navController.navigate(movieDetailRoute(movie.id))
                    },
                )
            }

            composable(MainTab.MOVIE_SEARCH.tag) {
                MovieSearchRoute(
                    viewModel = searchViewModel,
                    onSearchResultClick = {
                        navController.navigate(movieDetailRoute(it.id))
                    },
                )
            }

            composable(MainTab.SETTING.tag) {
                SettingRoute(
                    viewModel = settingViewModel,
                )
            }

            dialog(
                route = movieDetailRoute,
                arguments = listOf(navArgument(MovieSearchResultDetailViewModel.EXTRA_MOVIE_ID) {
                    type = NavType.IntType
                }),
                dialogProperties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    decorFitsSystemWindows = false,
                ),
            ) {
                MovieDetailRoute(
                    viewModel = hiltViewModel(),
                )
            }

            dialog(
                route = movieListRoute,
                arguments = listOf(navArgument(movieListProviderArgument) {
                    type = NavType.StringType
                }),
                dialogProperties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    decorFitsSystemWindows = false,
                ),
            ) {
                val providerClassName = it.arguments?.getString(movieListProviderArgument)
                    ?: throw IllegalStateException()
                val service =
                    movieListProviders.first { it::class.qualifiedName == providerClassName }
                val viewModel: MovieListViewModel = viewModel(
                    factory = MovieListViewModel.viewModelFactory(
                        movieListViewModelFactory,
                        service
                    ),
                )
                MovieListScreen(
                    movies = viewModel.movieList,
                    onCloseClick = {
                        navController.navigateUp()
                    },
                )
            }
        }

        MovieDatabaseNavigation(
            selectedItem = selectedTab,
            onSelected = onTabSelected,
        )
    }
}