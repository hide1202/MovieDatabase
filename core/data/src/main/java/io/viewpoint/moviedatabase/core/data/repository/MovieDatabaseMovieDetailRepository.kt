package io.viewpoint.moviedatabase.core.data.repository

import io.viewpoint.moviedatabase.api.MovieDetailApi
import io.viewpoint.moviedatabase.core.common.coroutines.suspendRunCatching
import io.viewpoint.moviedatabase.domain.model.Cast
import io.viewpoint.moviedatabase.domain.model.Credit
import io.viewpoint.moviedatabase.domain.model.Crew
import io.viewpoint.moviedatabase.domain.model.Keyword
import io.viewpoint.moviedatabase.domain.model.Movie
import io.viewpoint.moviedatabase.domain.model.MovieDetail
import io.viewpoint.moviedatabase.domain.model.WatchProvider
import io.viewpoint.moviedatabase.domain.repository.MovieDetailRepository
import javax.inject.Inject

class MovieDatabaseMovieDetailRepository @Inject constructor(
    private val movieDetailApi: MovieDetailApi
) : MovieDetailRepository {
    override suspend fun getMovieDetail(movieId: Int): MovieDetail? =
        suspendRunCatching {
            movieDetailApi.getMovieDetail(movieId)
        }.getOrNull()?.let { it.asDomain() }

    override suspend fun getCredits(movieId: Int): List<Credit> =
        suspendRunCatching {
            movieDetailApi.getMovieCredits(movieId)
        }.map {
            it.cast.map {
                Cast(
                    id = it.id,
                    name = it.name,
                    originalName = it.original_name,
                    gender = it.gender,
                    profilePath = it.profile_path,
                    adult = it.adult,
                    castId = it.cast_id,
                    character = it.character,
                    creditDd = it.credit_id,
                    knownForDepartment = it.known_for_department,
                    order = it.order,
                    popularity = it.popularity,
                )
            } + it.crew.map {
                Crew(
                    id = it.id,
                    name = it.name,
                    gender = it.gender,
                    originalName = it.original_name,
                    profilePath = it.profile_path,
                    adult = it.adult,
                    creditId = it.credit_id,
                    department = it.department,
                    job = it.job,
                    knownFordepartment = it.known_for_department,
                    popularity = it.popularity,
                )
            }
        }.getOrElse { emptyList() }


    override suspend fun getKeywords(movieId: Int): List<Keyword> =
        suspendRunCatching {
            movieDetailApi.getKeywords(movieId)
        }.map {
            it.keywords.map {
                Keyword(
                    id = it.id,
                    name = it.name
                )
            }
        }.getOrElse { emptyList() }

    override suspend fun getRecommendations(movieId: Int): List<Movie> =
        suspendRunCatching {
            movieDetailApi.getRecommendations(movieId)
        }.map {
            it.results.map { it.asDomain() }
        }.getOrElse { emptyList() }

    override suspend fun getWatchProviders(
        movieId: Int,
        countryCode: String
    ): WatchProvider? =
        suspendRunCatching {
            movieDetailApi.getWatchProviders(movieId)
        }.mapCatching {
            val watchProvider = it.results[countryCode]
            if (watchProvider != null) {
                watchProvider.let {
                    WatchProvider(
                        link = it.link,
                        buy = it.buy.map {
                            WatchProvider.Buy(
                                displayPriority = it.display_priority,
                                logoPath = it.logo_path,
                                providerId = it.provider_id,
                                providerName = it.provider_name,
                            )
                        },
                        flatrate = it.flatrate.map {
                            WatchProvider.Flatrate(
                                displayPriority = it.display_priority,
                                logoPath = it.logo_path,
                                providerId = it.provider_id,
                                providerName = it.provider_name,
                            )
                        },
                        rent = it.rent.map {
                            WatchProvider.Rent(
                                displayPriority = it.display_priority,
                                logoPath = it.logo_path,
                                providerId = it.provider_id,
                                providerName = it.provider_name,
                            )
                        },
                    )
                }
            } else {
                null
            }
        }.getOrNull()
}