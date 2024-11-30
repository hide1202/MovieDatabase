package io.viewpoint.moviedatabase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService
import io.viewpoint.moviedatabase.domain.preferences.addValue
import io.viewpoint.moviedatabase.domain.preferences.getValues
import io.viewpoint.moviedatabase.domain.preferences.removeValue
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val preferences: PreferencesService,
    private val pager: MovieSearchPager
) : ViewModel() {
    private var previousSearchJob: Job? = null
    private val _recentKeywords = MutableStateFlow<List<String>>(emptyList())

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    private val _keyword = MutableStateFlow("")
    val keyword: StateFlow<String> = _keyword.asStateFlow()
    fun onKeywordChanged(keyword: String) {
        _keyword.value = keyword
    }

    private val _searchSignalFlow = MutableStateFlow(keyword.value)
    val results: StateFlow<PagingData<SearchResultModel>> = _searchSignalFlow
        .flatMapLatest { keyword ->
            if (keyword.isEmpty()) {
                flowOf(PagingData.empty())
            } else {
                pager.pagingWithKeyword(keyword)
                    .onEach {
                        preferences.addValue(PreferencesKeys.SEARCHED_KEYWORDS, keyword)
                        loadRecentKeywords()
                    }
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())

    val resultCount = MutableLiveData<Int>()

    val recentKeywords: StateFlow<List<String>> = _recentKeywords

    var beforeSearchCommand: () -> Unit = {}

    init {
        viewModelScope.launch {
            loadRecentKeywords()
        }
    }

    val searchCommand = Command {
        val keyword = keyword.value

        beforeSearchCommand()

        _searchSignalFlow.value = keyword
    }

    suspend fun removeRecentKeyword(keyword: String) {
        preferences.removeValue(PreferencesKeys.SEARCHED_KEYWORDS, keyword)
        loadRecentKeywords()
    }

    fun clearSearchKeyword() {
        onKeywordChanged("")
        searchCommand.invoke()
    }

    private suspend fun loadRecentKeywords() {
        _recentKeywords.value = preferences.getValues(PreferencesKeys.SEARCHED_KEYWORDS)
    }
}