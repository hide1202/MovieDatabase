package io.viewpoint.moviedatabase.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.feature.search.R
import io.viewpoint.moviedatabase.api.dto.MovieDetailDto

@Composable
internal fun MovieDetailGenres(
    modifier: Modifier = Modifier,
    genres: List<MovieDetailDto.GenreDto>,
) {
    MovieDetailElement(modifier = modifier.fillMaxWidth()) {
        Column {
            MovieDetailSectionTitle(titleResId = R.string.detail_genres_label)
            Genres(genres)
        }
    }
}

@Composable
private fun Genres(genres: List<MovieDetailDto.GenreDto>) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(genres) {
            MovieDetailChip(text = it.name)
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
internal fun MovieDetailGenresPreview() {
    MovieDatabaseTheme {
        MovieDetailGenres(genres = List(10) { id ->
            MovieDetailDto.GenreDto(id = id, "Action")
        })
    }
}