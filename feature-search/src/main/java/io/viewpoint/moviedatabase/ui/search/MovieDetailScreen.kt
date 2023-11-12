package io.viewpoint.moviedatabase.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.designsystem.Palette

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
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
                want = true,
            )
            MovieDetailGenres(
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            )
            MovieDetailKeywords(
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            )
            MovieDetailOverview(
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
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