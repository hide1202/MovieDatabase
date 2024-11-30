@file:OptIn(ExperimentalGlideComposeApi::class)

package io.viewpoint.moviedatabase.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.feature.search.R
import io.viewpoint.moviedatabase.model.ui.CreditModel

@Composable
internal fun MovieDetailCredits(
    modifier: Modifier = Modifier,
    credits: List<CreditModel>,
) {
    MovieDetailElement(modifier = modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MovieDetailSectionTitle(titleResId = R.string.detail_credits_label)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(credits) {
                    Credit(it)
                }
            }
        }
    }
}

@Composable
private fun Credit(credit: CreditModel) {
    Column(modifier = Modifier.width(80.dp)) {
        GlideImage(
            modifier = Modifier
                .size(80.dp)
                .clip(shape = CircleShape),
            model = credit.profileUrl,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = credit.name.orEmpty(),
            style = LocalTextStyle.current.merge(MaterialTheme.typography.bodyMedium),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun MovieDetailCreditsPreview() {
    MovieDatabaseTheme {
        MovieDetailCredits(
            credits = List(10) {
                CreditModel(id = it, name = "Tobey Maguire Tobey Maguire", null)
            },
        )
    }
}