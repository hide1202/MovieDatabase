package io.viewpoint.moviedatabase.viewmodel.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.getOrElse
import io.viewpoint.moviedatabase.domain.repository.MovieRepository
import io.viewpoint.moviedatabase.domain.repository.WantToSeeRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapper
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.model.api.toMovie
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.viewmodel.Command
import kotlinx.coroutines.launch

class MovieSearchResultDetailViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
    private val wantToSeeRepository: WantToSeeRepository,
    private val resultMapper: SearchResultMapper
) : ViewModel() {
    private var result: SearchResultModel? = null
    private var _wantToSee = MutableLiveData(false)
    private var _genres = MutableLiveData<List<MovieDetail.Genre>>(emptyList())

    val wantToSee: LiveData<Boolean>
        get() = _wantToSee

    val genres: LiveData<List<MovieDetail.Genre>>
        get() = _genres

    val invertWantToSeeCommand = Command {
        val result = result ?: return@Command
        val wantToSee = _wantToSee.value ?: return@Command

        viewModelScope.launch {
            val either = if (wantToSee) {
                wantToSeeRepository.removeWantToSeeMovie(result.id)
            } else {
                wantToSeeRepository.addWantToSeeMovie(result.id)
            }.attempt()
                .suspended()

            if (either is Either.Right) {
                _wantToSee.value = !wantToSee
            }
        }
    }

    suspend fun loadWithMovieId(movieId: Int): SearchResultModel? {
        val result = movieRepository.getMovieDetail(movieId)
            ?.apply {
                _genres.value = this.genres
            }
            ?.toMovie()
            ?.let {
                resultMapper.map(it)
            }
        if (result != null) {
            loadWithResult(result)
        }
        return result
    }

    suspend fun loadWithResult(result: SearchResultModel) {
        this.result = result
        _wantToSee.value = wantToSeeRepository.hasWantToSeeMovie(result.id)
            .attempt()
            .suspended()
            .getOrElse { false }

        if (_genres.value?.isEmpty() == true) {
            movieRepository.getMovieDetail(result.id)
                ?.let {
                    _genres.value = it.genres
                }
        }
    }
}