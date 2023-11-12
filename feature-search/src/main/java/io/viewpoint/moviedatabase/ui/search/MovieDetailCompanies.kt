@file:OptIn(ExperimentalGlideComposeApi::class)

package io.viewpoint.moviedatabase.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.feature.search.R

@Composable
internal fun MovieDetailCompanies(
    modifier: Modifier = Modifier,
) {
    MovieDetailElement(modifier = modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MovieDetailSectionTitle(titleResId = R.string.detail_companies_label)
            LazyRow {
                items(count = 10) {
                    Company()
                }
            }
        }
    }
}

@Composable
private fun Company() {
    Column(modifier = Modifier.width(150.dp)) {
        GlideImage(
            modifier = Modifier
                .size(150.dp)
                .clip(shape = CircleShape),
            model = "",
            contentDescription = null,
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Marvel",
            style = LocalTextStyle.current.merge(MaterialTheme.typography.bodyMedium),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun MovieDetailCompaniesPreview() {
    MovieDatabaseTheme {
        MovieDetailCompanies()
    }
}