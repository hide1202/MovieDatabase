package io.viewpoint.moviedatabase.domain.search

import arrow.core.Option
import io.viewpoint.moviedatabase.domain.Mapper
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.model.api.MovieSearchResponse
import io.viewpoint.moviedatabase.model.ui.SearchResultModel

class SearchResultMapper(
    private val configurationRepository: ConfigurationRepository
) : Mapper<MovieSearchResponse.MovieSearchResult, SearchResultModel> {
    override suspend fun map(
        input: MovieSearchResponse.MovieSearchResult
    ): SearchResultModel {
        val imageUrl = configurationRepository.getImageBaseUrl()
        return SearchResultModel(
            id = input.id,
            title = input.title,
            overview = input.overview,
            posterUrl = toUrl(imageUrl) {
                input.poster_path
            },
            backdropUrl = toUrl(imageUrl) {
                input.backdrop_path
            },
            vote = input.vote_average,
            releaseDate = input.release_date
        )
    }

    private fun toUrl(
        optBaseUrl: Option<String>,
        pathSupplier: () -> String?
    ): String? = optBaseUrl
        .filter {
            pathSupplier() != null
        }
        .map { baseUrl ->
            "${baseUrl.trimEnd('/')}/${pathSupplier()?.trimStart('/')}"
        }
        .orNull()
}