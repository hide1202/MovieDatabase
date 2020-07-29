package io.viewpoint.moviedatabase.viewmodel.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.domain.preferences.addValue
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.util.PreferencesKeys
import io.viewpoint.moviedatabase.viewmodel.Command
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieSearchViewModel @ViewModelInject constructor(
    private val preferences: PreferencesService,
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
                    .collectLatest { pagingData ->
                        _results.postValue(pagingData)

                        preferences.addValue(PreferencesKeys.SEARCHED_KEYWORDS, keyword)
                    }
            }
        }
    }
}