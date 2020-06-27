package io.viewpoint.moviedatabase.platform.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import io.viewpoint.moviedatabase.platform.ui.search.model.SearchResultModel
import io.viewpoint.moviedatabase.platform.ui.search.paging.MovieSearchPager
import kotlinx.coroutines.flow.Flow

class MovieSearchViewModel @ViewModelInject constructor(
    private val pager: MovieSearchPager
) : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun searchMovies(keyword: String): Flow<PagingData<SearchResultModel>> =
        pager.pagingWithKeyword(keyword)
}