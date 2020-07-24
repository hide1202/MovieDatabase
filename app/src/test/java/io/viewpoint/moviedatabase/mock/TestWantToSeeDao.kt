package io.viewpoint.moviedatabase.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.domain.repository.dao.WantToSeeDao
import io.viewpoint.moviedatabase.domain.repository.entity.WantToSeeMovieEntity
import io.viewpoint.moviedatabase.model.api.MovieDetail
import io.viewpoint.moviedatabase.util.MoshiReader
import io.viewpoint.moviedatabase.util.ResponseReader

class TestWantToSeeDao : WantToSeeDao {
    private val detail: IO<MovieDetail> = IO.fx {
        !effect {
            ResponseReader.jsonFromFileAsync(
                "/responses/movie-detail.json",
                MoshiReader.moshi.adapter(MovieDetail::class.java)
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