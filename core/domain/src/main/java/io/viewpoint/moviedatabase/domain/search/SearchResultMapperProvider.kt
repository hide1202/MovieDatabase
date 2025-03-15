package io.viewpoint.moviedatabase.domain.search

import io.viewpoint.moviedatabase.domain.Mapper
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.api.dto.MovieDto
import io.viewpoint.moviedatabase.api.dto.MovieDetailDto
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import javax.inject.Inject

class SearchResultMapperProvider @Inject constructor(
    configurationRepository: ConfigurationRepository
) {
    val mapperFromMovie: Mapper<MovieDto, SearchResultModel> by lazy {
        FromMovie(configurationRepository)
    }

    val mapperFromMovieDetail: Mapper<MovieDetailDto, SearchResultModel> by lazy {
        FromMovieDetail(configurationRepository)
    }

    private class FromMovie(
        private val configurationRepository: ConfigurationRepository
    ) : Mapper<MovieDto, SearchResultModel> {
        override suspend fun map(input: MovieDto): SearchResultModel =
            SearchResultModel(
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
                productionCompanies = emptyList(),
                vote = input.vote_average,
                releaseDate = input.release_date
            )
    }

    private class FromMovieDetail(
        private val configurationRepository: ConfigurationRepository
    ) : Mapper<MovieDetailDto, SearchResultModel> {
        override suspend fun map(input: MovieDetailDto): SearchResultModel =
            SearchResultModel(
                id = input.id,
                title = input.title,
                originalTitle = input.original_title,
                overview = input.overview ?: "",
                posterUrl = configurationRepository.getImageUrl {
                    input.poster_path
                },
                backdropUrl = configurationRepository.getImageUrl {
                    input.backdrop_path
                },
                productionCompanies = input.production_companies
                    .map {
                        SearchResultModel.ProductionCompany(
                            id = it.id,
                            name = it.name,
                            logoUrl = configurationRepository.getImageUrl {
                                it.logo_path
                            }
                        )
                    },
                vote = input.vote_average,
                releaseDate = input.release_date
            )
    }
}