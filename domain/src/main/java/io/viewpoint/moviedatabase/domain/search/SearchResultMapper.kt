package io.viewpoint.moviedatabase.domain.search

import io.viewpoint.moviedatabase.domain.Mapper
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.model.api.Movie
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import javax.inject.Inject

class SearchResultMapper @Inject constructor(
    private val configurationRepository: ConfigurationRepository
) : Mapper<Movie, SearchResultModel> {
    override suspend fun map(
        input: Movie
    ): SearchResultModel = SearchResultModel(
        id = input.id,
        title = input.title,
        originalTitle = input.original_title,
        overview = input.overview,
        posterUrl = configurationRepository.getImageUrl {
            input.poster_path
        },
        backdropUrl = configurationRepository.getImageUrl {
            input.backdrop_path
        },
        vote = input.vote_average,
        releaseDate = input.release_date
    )
}