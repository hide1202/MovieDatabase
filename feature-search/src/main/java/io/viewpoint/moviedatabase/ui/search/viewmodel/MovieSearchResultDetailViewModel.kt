package io.viewpoint.moviedatabase.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.getOrElse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.viewpoint.moviedatabase.domain.CreditModelMapper
import io.viewpoint.moviedatabase.domain.repository.MovieDetailRepository
import io.viewpoint.moviedatabase.domain.repository.WantToSeeRepository
import io.viewpoint.moviedatabase.domain.search.SearchResultMapperProvider
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.model.ui.CreditModel
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.viewmodel.Command
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSearchResultDetailViewModel @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val wantToSeeRepository: WantToSeeRepository,
    private val resultMapperProvider: SearchResultMapperProvider,
    private val creditModelMapper: CreditModelMapper
) : ViewModel() {
    private var result: SearchResultModel? = null
    private val _wantToSee = MutableLiveData(false)
    private val _genres = MutableLiveData<List<MovieDetail.Genre>>(emptyList())
    private val _countries = MutableLiveData<List<String>>(emptyList())
    private val _credits = MutableLiveData<List<CreditModel>>(emptyList())
    private val _productionCompanies =
        MutableLiveData<List<SearchResultModel.ProductionCompany>>(emptyList())

    val wantToSee: LiveData<Boolean>
        get() = _wantToSee

    val genres: LiveData<List<MovieDetail.Genre>>
        get() = _genres

    val countries: LiveData<List<String>>
        get() = _countries

    val credits: LiveData<List<CreditModel>>
        get() = _credits

    val productionCompanies: LiveData<List<SearchResultModel.ProductionCompany>>
        get() = _productionCompanies

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
        val result = movieDetailRepository.getMovieDetail(movieId)
            ?.let {
                fillDetailData(it)
                resultMapperProvider.mapperFromMovieDetail.map(it)
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
            movieDetailRepository.getMovieDetail(result.id)
                ?.let {
                    fillDetailData(it)
                }
        }
    }

    private suspend fun fillDetailData(movieDetail: MovieDetail): SearchResultModel {
        _genres.value = movieDetail.genres
        _countries.value = movieDetail.production_countries.map { it.iso_3166_1 }

        // TODO call parallel with a move detail
        _credits.value = movieDetailRepository.getCredits(movieDetail.id)
            .map {
                creditModelMapper.map(it)
            }

        return resultMapperProvider.mapperFromMovieDetail
            .map(movieDetail)
            .also {
                _productionCompanies.value = it.productionCompanies
            }
    }
}