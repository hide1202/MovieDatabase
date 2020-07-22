package io.viewpoint.moviedatabase.domain.repository

import arrow.fx.IO
import io.viewpoint.moviedatabase.model.repository.WantToSeeMovie

interface WantToSeeRepository {
    fun getWantToSeeMovies(): IO<List<WantToSeeMovie>>

    suspend fun addWantToSeeMovie(wantToSeeMovie: WantToSeeMovie)

    suspend fun removeWantToSeeMovie(wantToSeeMovie: WantToSeeMovie)
}