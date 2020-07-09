package io.viewpoint.moviedatabase.platform.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.viewpoint.moviedatabase.domain.popular.PopularResultMapper
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieRepository
import io.viewpoint.moviedatabase.model.ui.PopularResultModel

class MainViewModel @ViewModelInject constructor(
    configurationRepository: ConfigurationRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val mapper = PopularResultMapper(configurationRepository)

    suspend fun getPopular(): List<PopularResultModel> =
        movieRepository.getPopular()
            .map {
                mapper.map(it)
            }
}