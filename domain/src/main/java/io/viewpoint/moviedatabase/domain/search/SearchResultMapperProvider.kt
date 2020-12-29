package io.viewpoint.moviedatabase.domain.search

import io.viewpoint.moviedatabase.domain.Mapper
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.model.api.Movie
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import javax.inject.Inject

class SearchResultMapperProvider @Inject constructor(
    configurationRepository: ConfigurationRepository
) {
    val mapperFromMovie: Mapper<Movie, SearchResultModel> by lazy {
        FromMovie(configurationRepository)
    }

    val mapperFromMovieDetail: Mapper<MovieDetail, SearchResultModel> by lazy {
        FromMovieDetail(configurationRepository)
    }

    private class FromMovie(
        private val configurationRepository: ConfigurationRepository
    ) : Mapper<Movie, SearchResultModel> {
        override suspend fun map(input: Movie): SearchResultModel =
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
    ) : Mapper<MovieDetail, SearchResultModel> {
        override suspend fun map(input: MovieDetail): SearchResultModel =
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