package io.viewpoint.moviedatabase.platform.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.domain.popular.PopularResultMapper
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.model.ui.PopularResultModel

class MainViewModel @ViewModelInject constructor(
    configurationRepository: ConfigurationRepository,
    private val movieApi: MovieApi
) : ViewModel() {
    private val mapper = PopularResultMapper(configurationRepository)

    suspend fun getPopular(): List<PopularResultModel> = try {
        movieApi.getPopular()
            .suspended()
            .results
            .map {
                mapper.map(it)
            }
    } catch (throwable: Throwable) {
        emptyList()
    }
}