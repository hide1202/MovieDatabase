package io.viewpoint.moviedatabase.domain.repository

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.MovieApi
import io.viewpoint.moviedatabase.domain.repository.dao.WantToSeeDao
import io.viewpoint.moviedatabase.domain.repository.entity.WantToSeeMovieEntity
import io.viewpoint.moviedatabase.model.api.MovieDetail
import javax.inject.Inject

class MovieDatabaseWantToSeeRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val dao: WantToSeeDao
) : WantToSeeRepository {
    override fun hasWantToSeeMovie(id: Int): IO<Boolean> = IO.fx {
        !effect {
            dao.getOne(id) != null
        }
    }

    override fun getWantToSeeMovies(): IO<List<MovieDetail>> = IO.fx {
        val ids: List<Int> = !effect {
            dao.getAll()
                .map {
                    it.id
                }
        }

        !ids.map { id ->
            movieApi.getMovieDetail(id)
        }.parSequence()
    }

    override fun addWantToSeeMovie(id: Int): IO<Unit> = IO.fx {
        !effect {
            dao.insert(WantToSeeMovieEntity(id))
        }
    }

    override fun removeWantToSeeMovie(id: Int): IO<Unit> = IO.fx {
        !effect {
            dao.delete(WantToSeeMovieEntity(id))
        }
    }
}
