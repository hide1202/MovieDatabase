package io.viewpoint.moviedatabase.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.feature.search.R

@Composable
fun MovieDetailOverview(
    modifier: Modifier = Modifier,
    overview: String,
) {
    MovieDetailElement(modifier = modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            MovieDetailSectionTitle(titleResId = R.string.detail_overview_label)
            Text(text = overview)
        }
    }
}

@Preview
@Composable
fun MovieDetailOverviewPreview() {
    MovieDatabaseTheme {
        MovieDetailOverview(
            overview = "토니 스타크(로버트 다우니 주니어)의 제안을 받고 시빌 워에 참여한 피터 파커(톰 홀랜드)은 다음 임무가 언제일지 설렘을 안고 하루하루를 보낸다. 토니는 MIT 진학 준비에 전념할 것을 권하지만 친절한"
        )
    }
}