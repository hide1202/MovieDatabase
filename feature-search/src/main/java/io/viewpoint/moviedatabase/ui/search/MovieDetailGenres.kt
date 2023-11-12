package io.viewpoint.moviedatabase.ui.search

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.feature.search.R

@Composable
internal fun MovieDetailGenres(
    modifier: Modifier = Modifier,
) {
    MovieDetailElement(modifier = modifier) {
        Column {
            MovieDetailSectionTitle(titleResId = R.string.detail_genres_label)
            Genres()
        }
    }
}

@Composable
private fun Genres() {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(count = 10) {
            Genre()
        }
    }
}

@Composable
private fun Genre() {
    Text(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(32.dp),
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp,
            ),
        text = "Action",
    )
}

@Preview
@Composable
internal fun MovieDetailGenresPreview() {
    MovieDatabaseTheme {
        MovieDetailGenres()
    }
}