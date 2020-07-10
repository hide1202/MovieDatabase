package io.viewpoint.moviedatabase.platform.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.platform.common.Command
import io.viewpoint.moviedatabase.platform.ui.search.paging.MovieSearchPager
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieSearchViewModel @ViewModelInject constructor(
    private val pager: MovieSearchPager
) : ViewModel() {
    private var previousSearchJob: Job? = null
    private val _results = MutableLiveData<PagingData<SearchResultModel>>()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val keyword: MutableLiveData<String> = MutableLiveData()

    val results: LiveData<PagingData<SearchResultModel>> = _results

    var beforeSearchCommand: () -> Unit = {}

    val searchCommand = Command {
        val keyword = keyword.value ?: return@Command
        if (keyword.trim().isEmpty()) {
            return@Command
        }

        beforeSearchCommand()
        viewModelScope.launch {
            previousSearchJob?.cancelAndJoin()
            previousSearchJob = launch {
                pager.pagingWithKeyword(keyword)
                    .collectLatest {
                        _results.postValue(it)
                    }
            }
        }
    }
}