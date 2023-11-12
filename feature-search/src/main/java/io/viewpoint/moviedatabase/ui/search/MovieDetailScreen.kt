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

private val SectionPaddingValue = PaddingValues(
    top = 8.dp,
    start = 8.dp,
    end = 8.dp
)

@Composable
fun MovieDetailScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Palette.light_gray),
    ) {
        item {
            MovieDetailHeader()
            MovieDetailVoteSection(
                modifier = Modifier.padding(SectionPaddingValue),
                want = true,
            )
            MovieDetailGenres(
                modifier = Modifier.padding(SectionPaddingValue),
            )
            MovieDetailKeywords(
                modifier = Modifier.padding(SectionPaddingValue),
            )
            MovieDetailOverview(
                modifier = Modifier.padding(SectionPaddingValue),
            )
            MovieDetailCredits(
                modifier = Modifier.padding(SectionPaddingValue),
            )
            MovieDetailCompanies(
                modifier = Modifier.padding(SectionPaddingValue),
            )
            MovieDetailRecommendations(
                modifier = Modifier.padding(SectionPaddingValue),
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
        MovieDetailScreen()
    }
}