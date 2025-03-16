package io.viewpoint.moviedatabase.core.data.repository

import io.viewpoint.moviedatabase.api.dto.MovieDetailDto
import io.viewpoint.moviedatabase.api.dto.MovieDto
import io.viewpoint.moviedatabase.domain.model.Movie
import io.viewpoint.moviedatabase.domain.model.MovieDetail

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

fun MovieDetailDto.asDomain(): MovieDetail {
    return MovieDetail(
        id = this.id,
        adult = this.adult,
        title = this.title,
        backdropPath = this.backdrop_path,
        belongsToCollection = this.belongs_to_collection,
        posterPath = this.poster_path,
        overview = this.overview,
        releaseDate = this.release_date,
        voteAverage = this.vote_average,
        voteCount = this.vote_count,
        runtime = this.runtime,
        genres = this.genres.map {
            MovieDetail.Genre(
                id = it.id,
                name = it.name,
            )
        },
        tagline = this.tagline,
        originalTitle = this.original_title,
        originalLanguage = this.original_language,
        status = this.status,
        budget = this.budget,
        revenue = this.revenue,
        homepage = this.homepage,
        imdbId = this.imdb_id,
        popularity = this.popularity,
        video = this.video,
        productionCompanies = this.production_companies.map {
            MovieDetail.ProductionCompany(
                id = it.id,
                logoPath = it.logo_path,
                name = it.name,
                originCountry = it.origin_country
            )
        },
        productionCountries = this.production_countries.map {
            MovieDetail.ProductionCountry(
                name = it.name,
                countryCode = it.iso_3166_1
            )
        },
        spokenLanguages = this.spoken_languages.map {
            MovieDetail.SpokenLanguage(
                name = it.name,
                languageCode = it.iso_639_1
            )
        },
    )
}