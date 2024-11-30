package io.viewpoint.moviedatabase.ui.search

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
import io.viewpoint.moviedatabase.model.ui.KeywordModel

@Composable
fun MovieDetailKeywords(
    modifier: Modifier = Modifier,
    keywords: List<KeywordModel>,
) {
    MovieDetailElement(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column {
            MovieDetailSectionTitle(titleResId = R.string.detail_keywords_label)
            LazyRow(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(keywords) {
                    MovieDetailChip(text = it.name)
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieDetailKeywordsPreview() {
    MovieDatabaseTheme {
        MovieDetailKeywords(keywords = List(10) {
            KeywordModel(id = it, name = "Keyword")
        })
    }
}
