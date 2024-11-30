@file:OptIn(ExperimentalComposeUiApi::class)

package io.viewpoint.moviedatabase.ui.search

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.feature.search.R
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.viewmodel.MovieSearchViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun MovieSearchRoute(
    viewModel: MovieSearchViewModel,
    onSearchResultClick: (SearchResultModel) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val keyword by viewModel.keyword.collectAsState()
    val results = viewModel.results.collectAsLazyPagingItems()
    val recentKeywords by viewModel.recentKeywords.collectAsState()

    val imeController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        viewModel.beforeSearchCommand = {
            imeController?.hide()
        }
    }

    MovieSearchScreen(
        searchKeyword = keyword,
        searchResult = results,
        recentKeywords = recentKeywords,
        onSearchKeywordChanged = {
            viewModel.onKeywordChanged(it)
        },
        onSearchClick = {
            viewModel.searchCommand.invoke()
        },
        onSearchKeywordCleared = {
            viewModel.clearSearchKeyword()
        },
        onRecentKeywordClick = {
            viewModel.onKeywordChanged(it)
            viewModel.searchCommand.invoke()
        },
        onRecentKeywordRemoveClick = {
            coroutineScope.launch {
                viewModel.removeRecentKeyword(it)
            }
        },
        onSearchResultClick = onSearchResultClick,
    )
}

@Composable
fun MovieSearchScreen(
    searchKeyword: String,
    searchResult: LazyPagingItems<SearchResultModel>,
    recentKeywords: List<String>,
    onSearchKeywordChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
    onSearchKeywordCleared: () -> Unit,
    onRecentKeywordClick: (String) -> Unit,
    onRecentKeywordRemoveClick: (String) -> Unit,
    onSearchResultClick: (SearchResultModel) -> Unit,
) {
    val itemCount = searchResult.itemCount

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SearchInputField(
            searchKeyword = searchKeyword,
            onSearchKeywordChanged = onSearchKeywordChanged,
            onSearchClick = onSearchClick,
            onSearchKeywordCleared = onSearchKeywordCleared,
        )
        if (itemCount > 0) {
            SearchResultList(
                result = searchResult,
                onSearchResultClick = onSearchResultClick,
            )
        } else {
            RecentSearchKeywordList(
                keywords = recentKeywords,
                onRecentKeywordClick = onRecentKeywordClick,
                onRecentKeywordRemoveClick = onRecentKeywordRemoveClick,
            )
        }
    }
}

@Composable
private fun SearchInputField(
    searchKeyword: String,
    onSearchKeywordChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
    onSearchKeywordCleared: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .weight(1f)
                .height(48.dp)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f),
                value = searchKeyword,
                onValueChange = onSearchKeywordChanged,
                textStyle = LocalTextStyle.current,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchClick() }
                )
            )
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onSearchKeywordCleared() },
                imageVector = Icons.Rounded.Clear,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        Image(
            modifier = Modifier
                .padding(end = 16.dp)
                .padding(8.dp)
                .size(24.dp)
                .clickable { onSearchClick() },
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
        )
    }
}

@Composable
private fun SearchResultList(
    result: LazyPagingItems<SearchResultModel>,
    onSearchResultClick: (SearchResultModel) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            count = result.itemCount,
            key = result.itemKey { it.id }
        ) {
            val item = result[it] ?: return@items
            SearchResult(
                item = item,
                onSearchResultClick = {
                    onSearchResultClick(it)
                },
            )
        }
        if (result.loadState.append is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun SearchResult(
    item: SearchResultModel,
    onSearchResultClick: (SearchResultModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable {
                onSearchResultClick(item)
            }
            .padding(16.dp),
    ) {
        GlideImage(
            modifier = Modifier.size(100.dp),
            model = item.posterUrl,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column(
            modifier = Modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = item.title,
                style = LocalTextStyle.current.merge(MaterialTheme.typography.titleLarge),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.overview,
                style = LocalTextStyle.current.merge(MaterialTheme.typography.bodyMedium),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun RecentSearchKeywordList(
    keywords: List<String>,
    onRecentKeywordClick: (String) -> Unit,
    onRecentKeywordRemoveClick: (String) -> Unit,
) {
    LazyColumn {
        items(keywords) {
            RecentSearchKeyword(
                keyword = it,
                onClick = onRecentKeywordClick,
                onRecentKeywordRemoveClick = onRecentKeywordRemoveClick,
            )
        }
    }
}

@Composable
private fun RecentSearchKeyword(
    keyword: String,
    onClick: (String) -> Unit,
    onRecentKeywordRemoveClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { onClick(keyword) },
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.CenterStart),
                text = keyword,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Image(
            modifier = Modifier.clickable {
                onRecentKeywordRemoveClick(keyword)
            },
            painter = painterResource(id = R.drawable.ic_remove),
            contentDescription = null,
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MovieSearchScreenPreview() {
    var keyword by remember { mutableStateOf("spiderman") }
    val result = flowOf(
        PagingData.from(
//            listOf(
//                DefaultSearchResultModel.copy(
//                    title = "스파이더맨: 홈커밍",
//                    overview = "토니 스타크(로버트 다우니 주니어)의 제안을 받고 시빌 워에 참여한 피터 파커(톰 홀랜드)은 다음 임무가 언제일지 설렘을 안고 하루하루를 보낸다. 토니는 MIT 진학 준비에 전념할 것을 권하지만 친절한",
//                )
//            )
            emptyList<SearchResultModel>()
        )
    ).collectAsLazyPagingItems()
    MovieDatabaseTheme {
        MovieSearchScreen(
            searchKeyword = keyword,
            searchResult = result,
            recentKeywords = listOf("spiderman"),
            onSearchKeywordChanged = { keyword = it },
            onSearchClick = {},
            onSearchKeywordCleared = {},
            onRecentKeywordClick = {},
            onRecentKeywordRemoveClick = {},
            onSearchResultClick = {},
        )
    }
}