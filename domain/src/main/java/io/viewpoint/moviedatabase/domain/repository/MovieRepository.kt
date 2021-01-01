package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.model.api.Credit
import io.viewpoint.moviedatabase.model.api.Movie
import io.viewpoint.moviedatabase.model.api.MovieDetail

interface MovieRepository {
    suspend fun getMovieDetail(movieId: Int): MovieDetail?

    suspend fun getPopular(): List<Movie>

    suspend fun getNowPlayings(): List<Movie>

    suspend fun getUpcoming(): List<Movie>

    suspend fun getTopRated(): List<Movie>

    suspend fun getCredits(movieId: Int): List<Credit>
}