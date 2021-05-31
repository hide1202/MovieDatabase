package io.viewpoint.moviedatabase.ui.home

import androidx.paging.PagingSource
import io.viewpoint.moviedatabase.domain.paging.MovieListPagingSource
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieRepository
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.ui.common.MovieListProvider
import javax.inject.Inject

class PopularMovieListProvider @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    private val movieRepository: MovieRepository
) : MovieListProvider {
    override fun provide(): PagingSource<Int, SearchResultModel> =
        MovieListPagingSource(configurationRepository) { page ->
            movieRepository.getPopular(page)
        }
}

class NowPlayingMovieListProvider @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    private val movieRepository: MovieRepository
) : MovieListProvider {
    override fun provide(): PagingSource<Int, SearchResultModel> =
        MovieListPagingSource(configurationRepository) { page ->
            movieRepository.getNowPlayings(page)
        }
}

class UpcomingMovieListProvider @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    private val movieRepository: MovieRepository
) : MovieListProvider {
    override fun provide(): PagingSource<Int, SearchResultModel> =
        MovieListPagingSource(configurationRepository) { page ->
            movieRepository.getUpcoming(page)
        }
}

class TopRatedMovieListProvider @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    private val movieRepository: MovieRepository
) : MovieListProvider {
    override fun provide(): PagingSource<Int, SearchResultModel> =
        MovieListPagingSource(configurationRepository) { page ->
            movieRepository.getTopRated(page)
        }
}