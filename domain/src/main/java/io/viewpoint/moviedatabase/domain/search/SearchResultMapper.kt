package io.viewpoint.moviedatabase.domain.search

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
            posterUrl = imageUrl
                .filter {
                    input.poster_path != null
                }
                .map { baseUrl ->
                    "${baseUrl.trimEnd('/')}/${input.poster_path?.trimStart('/')}"
                }
                .orNull()
        )
    }
}