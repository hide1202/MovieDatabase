package io.viewpoint.moviedatabase.domain.repository

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.domain.repository.dao.WantToSeeDao
import io.viewpoint.moviedatabase.domain.repository.entity.WantToSeeMovieEntity
import io.viewpoint.moviedatabase.model.repository.WantToSeeMovie
import javax.inject.Inject

class MovieDatabaseWantToSeeRepository @Inject constructor(
    private val dao: WantToSeeDao
) : WantToSeeRepository {
    override fun getWantToSeeMovies(): IO<List<WantToSeeMovie>> = IO.fx {
        !effect {
            dao.getAll()
                .map {
                    WantToSeeMovie(
                        id = it.id,
                        title = it.title,
                        poster_path = it.poster_path
                    )
                }
        }
    }

    override suspend fun addWantToSeeMovie(wantToSeeMovie: WantToSeeMovie) =
        dao.insert(
            WantToSeeMovieEntity(
                id = wantToSeeMovie.id,
                title = wantToSeeMovie.title,
                poster_path = wantToSeeMovie.poster_path
            )
        )

    override suspend fun removeWantToSeeMovie(wantToSeeMovie: WantToSeeMovie) =
        dao.delete(
            WantToSeeMovieEntity(
                id = wantToSeeMovie.id,
                title = wantToSeeMovie.title,
                poster_path = wantToSeeMovie.poster_path
            )
        )
}