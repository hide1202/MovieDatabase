package io.viewpoint.moviedatabase.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.designsystem.Palette
import io.viewpoint.moviedatabase.feature.search.R

@Composable
internal fun MovieDetailHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Palette.dark_gray),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            verticalAlignment = Alignment.Bottom,
        ) {
            /*android:id="@+id/poster"*/
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .background(Palette.gray),
            )
            Box(
                modifier = Modifier
                    .padding(start = 8.dp),
            ) {
                Column(modifier = Modifier.align(Alignment.BottomStart)) {
                    /*android:id="@+id/watch_providers"*/
                    WatchProviders()
                    /*android:id="@+id/flags"*/
                    Flags()
                    MovieTitle()
                    MovieOriginalTitle()
                }
            }
        }
    }
}

@Composable
private fun WatchProviders() {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        item {
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .background(Palette.yellow_green),
            )
        }
    }
}

@Composable
private fun Flags() {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        item {
            Image(
                modifier = Modifier.height(18.dp),
                painter = painterResource(id = R.drawable.flag_us),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun MovieTitle() {
    Text(
        text = "스파이더맨: 홈커밍",
        maxLines = 1,
        style = LocalTextStyle.current.merge(MaterialTheme.typography.titleLarge),
        color = MaterialTheme.colorScheme.surface,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun MovieOriginalTitle() {
    Text(
        text = "Spiderman",
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
        MovieDetailHeader()
    }
}