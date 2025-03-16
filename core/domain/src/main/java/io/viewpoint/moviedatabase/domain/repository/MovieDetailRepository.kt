package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.domain.model.Credit
import io.viewpoint.moviedatabase.domain.model.Keyword
import io.viewpoint.moviedatabase.domain.model.Movie
import io.viewpoint.moviedatabase.domain.model.MovieDetail
import io.viewpoint.moviedatabase.domain.model.WatchProvider

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: Int): MovieDetail?

    suspend fun getCredits(movieId: Int): List<Credit>

    suspend fun getKeywords(movieId: Int): List<Keyword>

    suspend fun getRecommendations(movieId: Int): List<Movie>

    suspend fun getWatchProviders(movieId: Int, countryCode: String): WatchProvider?
}