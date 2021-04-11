package io.viewpoint.moviedatabase.ui.search.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.domain.preferences.addValue
import io.viewpoint.moviedatabase.domain.preferences.getValues
import io.viewpoint.moviedatabase.domain.preferences.removeValue
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
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
    private val _recentKeywords = MutableLiveData<List<String>>()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val keyword: MutableLiveData<String> = MutableLiveData()

    val results: LiveData<PagingData<SearchResultModel>> = _results.cachedIn(this)

    val resultCount = MutableLiveData<Int>()

    val recentKeywords: LiveData<List<String>> = _recentKeywords

    var beforeSearchCommand: () -> Unit = {}

    init {
        loadRecentKeywords()
    }

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
                        loadRecentKeywords()
                    }
            }
        }
    }

    fun removeRecentKeyword(keyword: String) {
        preferences.removeValue(PreferencesKeys.SEARCHED_KEYWORDS, keyword)
    }

    private fun loadRecentKeywords() {
        _recentKeywords.value = preferences.getValues(PreferencesKeys.SEARCHED_KEYWORDS)
    }
}