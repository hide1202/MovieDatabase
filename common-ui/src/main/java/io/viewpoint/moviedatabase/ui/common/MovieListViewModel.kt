package io.viewpoint.moviedatabase.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import kotlinx.coroutines.flow.Flow

//@HiltViewModel
class MovieListViewModel @AssistedInject constructor(
    @Assisted private val provider: MovieListProvider
) : ViewModel() {
    val movieList: Flow<PagingData<SearchResultModel>> = Pager(
        PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            provider.provide()
        }
    ).flow.cachedIn(viewModelScope)

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