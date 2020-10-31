package io.viewpoint.moviedatabase.domain.popular

import io.viewpoint.moviedatabase.domain.Mapper
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.model.api.Movie
import io.viewpoint.moviedatabase.model.ui.HomeMovieListResultModel

class PopularResultMapper(
    private val configurationRepository: ConfigurationRepository
) : Mapper<Movie, HomeMovieListResultModel> {
    override suspend fun map(input: Movie): HomeMovieListResultModel =
        HomeMovieListResultModel(
            id = input.id,
            title = input.title,
            posterUrl = configurationRepository.getImageUrl {
                input.poster_path
            }
        )
}