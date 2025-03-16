package io.viewpoint.moviedatabase.viewmodel

import io.viewpoint.moviedatabase.api.dto.MovieDto
import io.viewpoint.moviedatabase.domain.model.Movie

fun MovieDto.asDomain(): Movie {
    return Movie(
        id = this.id,
        backdropPath = this.backdrop_path,
        genreIds = this.genre_ids,
        originalLanguage = this.original_language,
        originalTitle = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.poster_path,
        releaseDate = this.release_date,
        title = this.title,
        video = this.video,
        voteAverage = this.vote_average,
        voteCount = this.vote_count,
    )
}