package io.viewpoint.moviedatabase.viewmodel.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.getOrElse
import io.viewpoint.moviedatabase.domain.repository.WantToSeeRepository
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.viewmodel.Command
import kotlinx.coroutines.launch

class MovieSearchResultDetailViewModel @ViewModelInject constructor(
    private val wantToSeeRepository: WantToSeeRepository
) : ViewModel() {
    private var result: SearchResultModel? = null
    private var _wantToSee = MutableLiveData(false)

    val wantToSee: LiveData<Boolean>
        get() = _wantToSee

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

    suspend fun loadWithResult(result: SearchResultModel) {
        this.result = result
        viewModelScope.launch {
            _wantToSee.value = wantToSeeRepository.hasWantToSeeMovie(result.id)
                .attempt()
                .suspended()
                .getOrElse { false }
        }
    }
}