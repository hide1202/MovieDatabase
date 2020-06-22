package io.viewpoint.moviedatabase.platform.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import io.viewpoint.moviedatabase.api.SearchApi
import io.viewpoint.moviedatabase.platform.ui.search.model.SearchResultModel

class MovieSearchViewModel @ViewModelInject constructor(
    private val searchApi: SearchApi
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    suspend fun searchMovies(keyword: String): Either<Throwable, List<SearchResultModel>> {
        _isLoading.value = true
        val result = searchApi.searchMovie(keyword)
            .attempt()
            .suspended()
            .map { response ->
                response.results.map {
                    SearchResultModel(
                        id = it.id,
                        title = it.title,
                        overview = it.overview
                    )
                }
            }
        _isLoading.value = false
        return result
    }
}