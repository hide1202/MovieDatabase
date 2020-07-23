package io.viewpoint.moviedatabase.domain.repository

import arrow.core.Option
import arrow.fx.IO
import io.viewpoint.moviedatabase.model.repository.WantToSeeMovie

interface WantToSeeRepository {
    fun getWantToSeeMovie(id: Int): IO<Option<WantToSeeMovie>>

    fun getWantToSeeMovies(): IO<List<WantToSeeMovie>>

    fun addWantToSeeMovie(wantToSeeMovie: WantToSeeMovie): IO<Unit>

    fun removeWantToSeeMovie(wantToSeeMovie: WantToSeeMovie): IO<Unit>
}