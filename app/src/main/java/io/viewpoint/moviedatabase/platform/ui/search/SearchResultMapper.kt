package io.viewpoint.moviedatabase.platform.ui.search

import io.viewpoint.moviedatabase.api.model.MovieSearchResponse
import io.viewpoint.moviedatabase.base.Mapper
import io.viewpoint.moviedatabase.platform.ui.search.model.SearchResultModel
import io.viewpoint.moviedatabase.repository.ConfigurationRepository
import javax.inject.Inject

class SearchResultMapper @Inject constructor(
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