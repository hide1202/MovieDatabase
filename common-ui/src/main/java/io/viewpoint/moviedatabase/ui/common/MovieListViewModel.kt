package io.viewpoint.moviedatabase.ui.common

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//@HiltViewModel
class MovieListViewModel @AssistedInject constructor(
    @Assisted private val provider: MovieListProvider
) : ViewModel() {
    private val _movieList = MutableLiveData<PagingData<SearchResultModel>>()

    val movieList: LiveData<PagingData<SearchResultModel>> = _movieList

    init {
        viewModelScope.launch {
            Pager(
                PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    provider.provide()
                })
                .flow
                .collectLatest {
                    _movieList.value = it
                }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(provider: MovieListProvider): MovieListViewModel
    }

    companion object {
        fun viewModelFactory(
            factory: Factory,
            provider: MovieListProvider
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return factory.create(provider) as T
                }
            }
    }
}