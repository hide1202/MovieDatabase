package io.viewpoint.moviedatabase.ui.search

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.feature.search.R
import io.viewpoint.moviedatabase.viewmodel.Command

@Composable
internal fun MovieDetailVoteSection(
    modifier: Modifier = Modifier,
    voteScore: Double,
    releaseDate: String?,
    want: Boolean,
    onInvertWantToSeeCommand: Command,
) {
    MovieDetailElement(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                text = stringResource(id = R.string.detail_vote, voteScore),
                textAlign = TextAlign.Center,
            )
            if (releaseDate != null) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = releaseDate,
                    textAlign = TextAlign.Center,
                )
            }
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onInvertWantToSeeCommand()
                    },
                painter = painterResource(id = if (want) R.drawable.ic_full_heart else R.drawable.ic_empty_heart),
                contentDescription = null,
            )
        }
    }
}

@Preview(backgroundColor = 0xFF808080)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
internal fun MovieDetailVoteSectionPreview() {
    MovieDatabaseTheme {
        MovieDetailVoteSection(
            voteScore = 1.0,
            releaseDate = "2020-05-01",
            want = true,
            onInvertWantToSeeCommand = Command { },
        )
    }
}