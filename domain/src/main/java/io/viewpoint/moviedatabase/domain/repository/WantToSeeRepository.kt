package io.viewpoint.moviedatabase.domain.repository

import arrow.fx.IO
import io.viewpoint.moviedatabase.model.api.MovieDetail

interface WantToSeeRepository {
    fun hasWantToSeeMovie(id: Int): IO<Boolean>

    fun getWantToSeeMovies(): IO<List<MovieDetail>>

    fun addWantToSeeMovie(id: Int): IO<Unit>

    fun removeWantToSeeMovie(id: Int): IO<Unit>
}