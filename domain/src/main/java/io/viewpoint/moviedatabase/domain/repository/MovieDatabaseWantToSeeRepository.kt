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
    override fun getWantToSeeMovie(id: Int): IO<WantToSeeMovie> = IO.fx {
        !effect {
            dao.getOne(id)
                .toWantToSeeMovie()
        }
    }

    override fun getWantToSeeMovies(): IO<List<WantToSeeMovie>> = IO.fx {
        !effect {
            dao.getAll()
                .map { entity ->
                    entity.toWantToSeeMovie()
                }
        }
    }

    override fun addWantToSeeMovie(wantToSeeMovie: WantToSeeMovie): IO<Unit> = IO.fx {
        !effect {
            dao.insert(wantToSeeMovie.toEntity())
        }
    }


    override fun removeWantToSeeMovie(wantToSeeMovie: WantToSeeMovie): IO<Unit> = IO.fx {
        !effect {
            dao.delete(wantToSeeMovie.toEntity())
        }
    }
}

fun WantToSeeMovie.toEntity(): WantToSeeMovieEntity =
    WantToSeeMovieEntity(
        id = id,
        title = title,
        poster_path = poster_path
    )

fun WantToSeeMovieEntity.toWantToSeeMovie(): WantToSeeMovie =
    WantToSeeMovie(
        id = id,
        title = title,
        poster_path = poster_path
    )