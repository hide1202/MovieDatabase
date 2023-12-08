@file:OptIn(ExperimentalGlideComposeApi::class)

package io.viewpoint.moviedatabase.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.designsystem.Palette
import io.viewpoint.moviedatabase.model.ui.DefaultSearchResultModel
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.model.ui.WatchProviderModel
import io.viewpoint.moviedatabase.util.Flags

@Composable
internal fun MovieDetailHeader(
    result: SearchResultModel?,
    watchProviderModel: WatchProviderModel?,
    countries: List<String>,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Palette.dark_gray),
    ) {
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            model = result?.backdropUrl,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            verticalAlignment = Alignment.Bottom,
        ) {
            GlideImage(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .background(Palette.gray),
                model = result?.posterUrl,
                contentDescription = null,
            )
            Box(
                modifier = Modifier
                    .padding(start = 8.dp),
            ) {
                Column(
                    modifier = Modifier.align(Alignment.BottomStart),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (watchProviderModel != null) {
                        WatchProviders(watchProviderModel)
                    }
                    Flags(countries)

                    result?.title?.let { title ->
                        MovieTitle(title)
                    }

                    result?.originalTitle?.let { originalTitle ->
                        MovieOriginalTitle(originalTitle)
                    }
                }
            }
        }
    }
}

@Composable
private fun WatchProviders(
    watchProviderModel: WatchProviderModel,
) {
    val watchProviders by remember(watchProviderModel) {
        derivedStateOf {
            watchProviderModel.providers.values.flatten().distinctBy { it.providerId }
        }
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = watchProviders) {
            GlideImage(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape),
                model = it.logoUrl,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun Flags(countries: List<String>) {
    val context = LocalContext.current
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items = countries) {
            Image(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape),
                painter = rememberDrawablePainter(Flags.getDrawable(context, it)),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun MovieTitle(title: String) {
    Text(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
        text = title,
        maxLines = 1,
        style = LocalTextStyle.current.merge(MaterialTheme.typography.titleLarge),
        color = MaterialTheme.colorScheme.onPrimary,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun MovieOriginalTitle(originalTitle: String) {
    Text(
        text = originalTitle,
        maxLines = 1,
        style = LocalTextStyle.current.merge(MaterialTheme.typography.titleSmall),
        color = Palette.searchDescriptionText,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview
@Composable
fun MovieDetailHeaderPreview() {
    MovieDatabaseTheme {
        MovieDetailHeader(
            result = DefaultSearchResultModel.copy(
                title = "스파이더맨: 홈커밍",
                originalTitle = "Spiderman"
            ),
            watchProviderModel = WatchProviderModel(emptyMap()),
            countries = listOf("en"),
        )
    }
}