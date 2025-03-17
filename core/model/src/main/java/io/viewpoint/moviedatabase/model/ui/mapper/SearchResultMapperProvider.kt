package io.viewpoint.moviedatabase.model.ui.mapper

import io.viewpoint.moviedatabase.domain.Mapper
import io.viewpoint.moviedatabase.domain.model.Movie
import io.viewpoint.moviedatabase.domain.model.MovieDetail
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
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
                originalTitle = input.originalTitle,
                overview = input.overview,
                posterUrl = configurationRepository.getImageUrl {
                    input.posterPath
                },
                backdropUrl = configurationRepository.getImageUrl {
                    input.backdropPath
                },
                productionCompanies = emptyList(),
                vote = input.voteAverage,
                releaseDate = input.releaseDate,
            )
    }

    private class FromMovieDetail(
        private val configurationRepository: ConfigurationRepository
    ) : Mapper<MovieDetail, SearchResultModel> {
        override suspend fun map(input: MovieDetail): SearchResultModel =
            SearchResultModel(
                id = input.id,
                title = input.title,
                originalTitle = input.originalTitle,
                overview = input.overview ?: "",
                posterUrl = configurationRepository.getImageUrl {
                    input.posterPath
                },
                backdropUrl = configurationRepository.getImageUrl {
                    input.backdropPath
                },
                productionCompanies = input.productionCompanies
                    .map {
                        SearchResultModel.ProductionCompany(
                            id = it.id,
                            name = it.name,
                            logoUrl = configurationRepository.getImageUrl {
                                it.logoPath
                            }
                        )
                    },
                vote = input.voteAverage,
                releaseDate = input.releaseDate
            )
    }
}