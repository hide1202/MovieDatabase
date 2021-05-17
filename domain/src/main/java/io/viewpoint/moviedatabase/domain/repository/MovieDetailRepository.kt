package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.model.api.Credit
import io.viewpoint.moviedatabase.model.api.Keyword
import io.viewpoint.moviedatabase.model.api.MovieDetail

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: Int): MovieDetail?

    suspend fun getCredits(movieId: Int): List<Credit>

    suspend fun getKeywords(movieId: Int): List<Keyword>
}