package io.viewpoint.moviedatabase.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
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
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    factory.create(provider)
                }
            }
        }
    }
}