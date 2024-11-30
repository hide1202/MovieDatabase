package io.viewpoint.moviedatabase.ui.common

import androidx.paging.PagingSource
import io.viewpoint.moviedatabase.model.ui.SearchResultModel

interface MovieListProvider {
    fun provide(): PagingSource<Int, SearchResultModel>
}
