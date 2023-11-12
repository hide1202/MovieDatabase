package io.viewpoint.moviedatabase.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.feature.search.R

@Composable
fun MovieDetailKeywords(
    modifier: Modifier = Modifier,
) {
    MovieDetailElement(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column {
            MovieDetailSectionTitle(titleResId = R.string.detail_keywords_label)
            LazyRow(modifier = Modifier.padding(top = 16.dp)) {
                items(count = 10) {
                    MovieDetailChip(text = "Action")
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieDetailKeywordsPreview() {
    MovieDatabaseTheme {
        MovieDetailKeywords()
    }
}
