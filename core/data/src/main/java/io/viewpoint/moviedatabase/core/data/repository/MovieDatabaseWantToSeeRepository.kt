package io.viewpoint.moviedatabase.core.data.repository

import io.viewpoint.moviedatabase.api.MovieDetailApi
import io.viewpoint.moviedatabase.domain.repository.dao.WantToSeeDao
import io.viewpoint.moviedatabase.domain.repository.entity.WantToSeeMovieEntity
import io.viewpoint.moviedatabase.api.dto.MovieDetailDto
import io.viewpoint.moviedatabase.domain.repository.WantToSeeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MovieDatabaseWantToSeeRepository @Inject constructor(
    private val movieDetailApi: MovieDetailApi,
    private val dao: WantToSeeDao
) : WantToSeeRepository {
    override suspend fun hasWantToSeeMovie(id: Int): Boolean {
        return dao.getOne(id) != null
    }

    override suspend fun getWantToSeeMovies(): List<MovieDetailDto> = coroutineScope {
        val ids: List<Int> = dao.getAll()
            .map {
                it.id
            }

        ids.map { id ->
            async { movieDetailApi.getMovieDetail(id) }
        }.awaitAll()
    }

    override suspend fun addWantToSeeMovie(id: Int) {
        dao.insert(WantToSeeMovieEntity(id))
    }

    override suspend fun removeWantToSeeMovie(id: Int) {
        dao.delete(WantToSeeMovieEntity(id))
    }
}
