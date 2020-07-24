package io.viewpoint.moviedatabase.model.api

data class MovieDetail(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: Any?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    data class Genre(
        val id: Int,
        val name: String
    )

    data class ProductionCompany(
        val id: Int,
        val logo_path: String?,
        val name: String,
        val origin_country: String
    )

    data class ProductionCountry(
        val iso_3166_1: String,
        val name: String
    )

    data class SpokenLanguage(
        val iso_639_1: String,
        val name: String
    )
}

fun MovieDetail.toMovie(): Movie = Movie(
    adult = this.adult,
    backdrop_path = this.backdrop_path,
    genre_ids = this.genres.map { it.id },
    id = this.id,
    original_language = this.original_language,
    original_title = this.original_title,
    overview = this.overview ?: "",
    popularity = this.popularity,
    poster_path = this.poster_path,
    release_date = this.release_date,
    title = this.title,
    video = this.video,
    vote_average = this.vote_average,
    vote_count = this.vote_count
)