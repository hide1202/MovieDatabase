package io.viewpoint.moviedatabase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import io.viewpoint.moviedatabase.ui.common.MovieListProvider
import io.viewpoint.moviedatabase.ui.home.NowPlayingMovieListProvider
import io.viewpoint.moviedatabase.ui.home.PopularMovieListProvider
import io.viewpoint.moviedatabase.ui.home.TopRatedMovieListProvider
import io.viewpoint.moviedatabase.ui.home.UpcomingMovieListProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieListProviderModule {
    @Binds
    @IntoSet
    abstract fun popularMovieListProvider(provider: PopularMovieListProvider): MovieListProvider

    @Binds
    @IntoSet
    abstract fun nowPlayingMovieListProvider(provider: NowPlayingMovieListProvider): MovieListProvider

    @Binds
    @IntoSet
    abstract fun upcomingMovieListProvider(provider: UpcomingMovieListProvider): MovieListProvider

    @Binds
    @IntoSet
    abstract fun topRatedMovieListProvider(provider: TopRatedMovieListProvider): MovieListProvider
}