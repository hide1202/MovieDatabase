@file:OptIn(ExperimentalGlideComposeApi::class)

package io.viewpoint.moviedatabase.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.model.ui.SearchResultModel

@Composable
fun HomeMovieList(
    items: List<SearchResultModel>,
    isCircle: Boolean,
    onMovieClicked: (SearchResultModel) -> Unit,
) {
    LazyRow {
        items(items) {
            HomeMovieListElement(
                model = it,
                isCircle = isCircle,
                onMovieClicked = onMovieClicked,
            )
        }
    }
}

@Composable
private fun HomeMovieListElement(
    model: SearchResultModel,
    isCircle: Boolean,
    onMovieClicked: (SearchResultModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable { onMovieClicked(model) },
    ) {
        GlideImage(
            modifier = Modifier
                .then(
                    if (isCircle) {
                        Modifier
                            .size(150.dp)
                            .padding(all = 8.dp)
                    } else {
                        Modifier
                            .width(150.dp)
                            .height(200.dp)
                    }
                ),
            model = model.posterUrl,
            contentScale = ContentScale.Fit,
            requestBuilderTransform = {
                if (isCircle) {
                    it.circleCrop()
                } else {
                    it
                }
            },
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(all = 8.dp),
            text = model.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun HomeMovieListElementPreview() {
    MovieDatabaseTheme {
        HomeMovieListElement(
            model = PreviewSearchResultModel
                .copy(
                    title = "스파이더맨: 홈커밍",
                ),
            isCircle = false,
            onMovieClicked = {},
        )
    }
}

internal val PreviewSearchResultModel = SearchResultModel(
    id = 0,
    title = "",
    originalTitle = "",
    overview = "",
    posterUrl = "",
    backdropUrl = "",
    productionCompanies = emptyList(),
    vote = 0.0,
    releaseDate = "",
)