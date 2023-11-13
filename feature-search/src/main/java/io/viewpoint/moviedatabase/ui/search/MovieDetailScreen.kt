package io.viewpoint.moviedatabase.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.designsystem.Palette
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.model.ui.DefaultSearchResultModel
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.viewmodel.Command
import io.viewpoint.moviedatabase.viewmodel.MovieDetailUiState

private val SectionPaddingValue = PaddingValues(
    top = 8.dp,
    start = 8.dp,
    end = 8.dp
)

@Composable
fun MovieDetailScreen(
    result: SearchResultModel?,
    uiState: MovieDetailUiState,
    onInvertWantToSeeCommand: Command,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Palette.light_gray),
    ) {
        item {
            MovieDetailHeader(
                result = result,
                watchProviderModel = uiState.watchProviders,
                countries = uiState.countries,
            )
            MovieDetailVoteSection(
                modifier = Modifier.padding(SectionPaddingValue),
                voteScore = result?.vote ?: 0.0,
                releaseDate = result?.releaseDate,
                want = uiState.wantToSee,
                onInvertWantToSeeCommand = onInvertWantToSeeCommand,
            )
            MovieDetailGenres(
                modifier = Modifier.padding(SectionPaddingValue),
                genres = uiState.genres,
            )
            MovieDetailKeywords(
                modifier = Modifier.padding(SectionPaddingValue),
                keywords = uiState.keywords,
            )
            MovieDetailOverview(
                modifier = Modifier.padding(SectionPaddingValue),
                overview = result?.overview.orEmpty(),
            )
            MovieDetailCredits(
                modifier = Modifier.padding(SectionPaddingValue),
                credits = uiState.credits,
            )
            MovieDetailCompanies(
                modifier = Modifier.padding(SectionPaddingValue),
                companies = uiState.productionCompanies,
            )
            MovieDetailRecommendations(
                modifier = Modifier.padding(SectionPaddingValue),
                recommendations = uiState.recommendations,
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp),
            )
        }
    }
}

@Preview
@Composable
fun MovieDetailScreenPreview() {
    MovieDatabaseTheme {
        MovieDetailScreen(
            result = DefaultSearchResultModel,
            uiState = MovieDetailUiState(
                genres = listOf(
                    MovieDetail.Genre(id = 0, name = "Fantasy"),
                    MovieDetail.Genre(id = 1, name = "Action"),
                )
            ),
            onInvertWantToSeeCommand = Command { },
        )
    }
}