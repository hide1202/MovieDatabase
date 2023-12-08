package io.viewpoint.moviedatabase.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.feature.home.R
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.ui.common.MovieListProvider
import io.viewpoint.moviedatabase.viewmodel.MainViewModel
import kotlin.reflect.KClass

enum class Section(
    @StringRes val labelResId: Int,
    val providerClass: KClass<out MovieListProvider>?,
) {
    WANT_TO_SEE(
        labelResId = R.string.want_to_see_header,
        providerClass = null,
    ),
    POPULAR(
        labelResId = R.string.popular_header,
        providerClass = PopularMovieListProvider::class,
    ),
    NOW_PLAYING(
        labelResId = R.string.now_playing_header,
        providerClass = NowPlayingMovieListProvider::class,
    ),
    UPCOMING(
        labelResId = R.string.upcoming_header,
        providerClass = UpcomingMovieListProvider::class,
    ),
    TOP_RATED(
        labelResId = R.string.top_rated_header,
        providerClass = TopRatedMovieListProvider::class,
    ),
}

data class HomeSection(
    val section: Section,
    val list: List<SearchResultModel>,
    val circle: Boolean,
)

@Composable
fun HomeRoute(
    viewModel: MainViewModel,
    onMoreClicked: (Section) -> Unit,
    onMovieClicked: (SearchResultModel) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    MovieDatabaseTheme {
        HomeScreen(
            wantToSeeList = uiState.wantToSeeList,
            popularList = uiState.popularList,
            nowPlayingList = uiState.nowPlayingList,
            upcomingList = uiState.upcomingList,
            topRatedList = uiState.topRatedList,
            onMoreClicked = onMoreClicked,
            onMovieClicked = onMovieClicked,
        )
    }
}

@Composable
internal fun HomeScreen(
    wantToSeeList: List<SearchResultModel>,
    popularList: List<SearchResultModel>,
    nowPlayingList: List<SearchResultModel>,
    upcomingList: List<SearchResultModel>,
    topRatedList: List<SearchResultModel>,
    onMoreClicked: (Section) -> Unit,
    onMovieClicked: (SearchResultModel) -> Unit,
) {
    HomeScreen(
        sections = listOf(
            HomeSection(
                section = Section.WANT_TO_SEE,
                list = wantToSeeList,
                circle = true,
            ),
            HomeSection(
                section = Section.POPULAR,
                list = popularList,
                circle = false,
            ),
            HomeSection(
                section = Section.NOW_PLAYING,
                list = nowPlayingList,
                circle = false,
            ),
            HomeSection(
                section = Section.UPCOMING,
                list = upcomingList,
                circle = false,
            ),
            HomeSection(
                section = Section.TOP_RATED,
                list = topRatedList,
                circle = false,
            ),
        ),
        onMoreClicked = onMoreClicked,
        onMovieClicked = onMovieClicked,
    )
}

@Composable
private fun HomeScreen(
    sections: List<HomeSection>,
    onMoreClicked: (Section) -> Unit,
    onMovieClicked: (SearchResultModel) -> Unit,
) {
    LazyColumn {
        items(sections) { section ->
            if (section.list.isNotEmpty()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Label(
                        section = section.section,
                        onMoreClicked = onMoreClicked,
                    )
                    HomeMovieList(
                        items = section.list,
                        posterShape = if (section.circle) {
                            CircleShape
                        } else {
                            RectangleShape
                        },
                        onMovieClicked = onMovieClicked,
                    )
                }
            }
        }
    }
}

@Composable
private fun Label(
    section: Section,
    onMoreClicked: (Section) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Text(
            text = stringResource(id = section.labelResId),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )

        if (section.providerClass != null) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        onMoreClicked(section)
                    },
                text = stringResource(id = R.string.more),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MovieDatabaseTheme {
        HomeScreen(
            wantToSeeList = listOf(PreviewSearchResultModel),
            popularList = listOf(PreviewSearchResultModel),
            nowPlayingList = listOf(PreviewSearchResultModel),
            upcomingList = listOf(PreviewSearchResultModel),
            topRatedList = listOf(PreviewSearchResultModel),
            onMoreClicked = {},
            onMovieClicked = {},
        )
    }
}