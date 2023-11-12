package io.viewpoint.moviedatabase.ui.search

import androidx.compose.foundation.Image
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

/*
    <LinearLayout
        android:id="@+id/vote_section"
        style="@style/DetailContentSection"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="16dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/backdrop">

        <TextView
            android:id="@+id/vote"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:layout_weight="1"
            android:gravity="center"
            android:padding="8dp"
            android:text="@{@string/detail_vote(String.valueOf(result.vote))}"
            android:textColor="@color/default_text_color"
            android:textSize="14sp"
            tools:ignore="SpUsage"
            tools:text="@string/detail_vote" />

        <TextView
            android:id="@+id/release_date"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:gravity="center"
            android:text="@{result.releaseDate ?? @string/not_yet_date}"
            tools:text="2021-05-05" />

        <FrameLayout
            android:id="@+id/want_to_see"
            android:layout_width="32dp"
            android:layout_height="match_parent"
            android:layout_marginStart="8dp"
            app:command="@{vm.invertWantToSeeCommand}"
            app:layout_constraintBottom_toBottomOf="@id/genreList"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="@id/genreList">

            <CheckBox
                android:layout_width="32dp"
                android:layout_height="32dp"
                android:layout_gravity="center"
                android:background="@drawable/ic_heart_check"
                android:button="@null"
                android:checked="@{vm.wantToSee}"
                android:clickable="false"
                android:focusable="false" />
        </FrameLayout>
    </LinearLayout>
*/
@Composable
internal fun MovieDetailVoteSection(
    modifier: Modifier = Modifier,
    want: Boolean,
) {
    MovieDetailElement(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                text = stringResource(id = R.string.detail_vote),
                textAlign = TextAlign.Center,
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "2021-05-05",
                textAlign = TextAlign.Center,
            )
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = if (want) R.drawable.ic_full_heart else R.drawable.ic_empty_heart),
                contentDescription = null,
            )
        }
    }
}

@Preview(backgroundColor = 0xFF808080)
@Composable
internal fun MovieDetailVoteSectionPreview() {
    MovieDatabaseTheme {
        MovieDetailVoteSection(want = true)
    }
}