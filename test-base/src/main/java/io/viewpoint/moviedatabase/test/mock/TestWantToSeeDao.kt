package io.viewpoint.moviedatabase.test.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.domain.repository.dao.WantToSeeDao
import io.viewpoint.moviedatabase.domain.repository.entity.WantToSeeMovieEntity
import io.viewpoint.moviedatabase.model.api.MovieDetail

class TestWantToSeeDao : WantToSeeDao {
    private val detail: IO<MovieDetail> = IO.fx {
        !effect {
            io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                "responses/movie-detail.json",
                io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(MovieDetail::class.java)
            )
        }
    }

    override suspend fun getOne(id: Int): WantToSeeMovieEntity? {
        return WantToSeeMovieEntity(detail.suspended().id)
    }

    override suspend fun getAll(): List<WantToSeeMovieEntity> {
        return listOf(WantToSeeMovieEntity(detail.suspended().id))
    }

    override suspend fun insert(entity: WantToSeeMovieEntity) = Unit

    override suspend fun delete(entity: WantToSeeMovieEntity) = Unit
}