@file:OptIn(ExperimentalGlideComposeApi::class)

package io.viewpoint.moviedatabase.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.designsystem.Palette
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun MovieListScreen(
    movies: Flow<PagingData<SearchResultModel>>,
    onCloseClick: () -> Unit,
) {
    val items: LazyPagingItems<SearchResultModel> = movies.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.height(60.dp)) {
            Image(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .size(20.dp)
                    .align(Alignment.TopStart)
                    .clickable(onClick = onCloseClick),
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
            )
        }
        LazyColumn {
            items(
                count = items.itemCount,
                key = items.itemKey { it.id },
            ) { index ->
                val item = items[index] ?: return@items

                Movie(
                    posterUrl = item.posterUrl,
                    title = item.title,
                    overview = item.overview,
                )
            }
        }
    }
}

@Composable
private fun Movie(
    posterUrl: String?,
    title: String,
    overview: String,
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        GlideImage(
            modifier = Modifier.size(100.dp),
            model = posterUrl,
            contentDescription = null,
        )
        Column(
            modifier = Modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = title,
                style = LocalTextStyle.current.merge(MaterialTheme.typography.titleLarge),
                maxLines = 1,
            )
            Text(
                text = overview,
                style = LocalTextStyle.current.merge(MaterialTheme.typography.bodyMedium),
                color = Palette.searchDescriptionText,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    MovieDatabaseTheme {
        Surface {
            MovieListScreen(
                movies = flowOf(PagingData.from(
                    List(size = 12) {
                        PreviewSearchResultModel.copy(
                            id = it,
                            title = "스파이더맨: 홈커밍",
                            overview = "토니 스타크(로버트 다우니 주니어)의 제안을 받고 시빌 워에 참여한 피터 파커(톰 홀랜드)은 다음 임무가 언제일지 설렘을 안고 하루하루를 보낸다. 토니는 MIT 진학 준비에 전념할 것을 권하지만 친절한",
                        )
                    }
                )),
                onCloseClick = {},
            )
        }
    }
}

@Preview
@Composable
fun MoviePreview() {
    MovieDatabaseTheme {
        Surface {
            Movie(
                posterUrl = "",
                title = "스파이더맨: 홈커밍",
                overview = "토니 스타크(로버트 다우니 주니어)의 제안을 받고 시빌 워에 참여한 피터 파커(톰 홀랜드)은 다음 임무가 언제일지 설렘을 안고 하루하루를 보낸다. 토니는 MIT 진학 준비에 전념할 것을 권하지만 친절한",
            )
        }
    }
}

private val PreviewSearchResultModel = SearchResultModel(
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