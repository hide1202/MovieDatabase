package io.viewpoint.moviedatabase.domain.model

data class MovieDetail(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val belongsToCollection: Any?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int?,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
) {
    data class Genre(
        val id: Int,
        val name: String
    )

    data class ProductionCompany(
        val id: Int,
        val logoPath: String?,
        val name: String,
        val originCountry: String
    )

    /**
     * @param name Country name
     * @param countryCode Country code (ISO 3166-1 code)
     */
    data class ProductionCountry(
        val name: String,
        val countryCode: String,
    )

    /**
     * @param name Language name
     * @param languageCode Language code (ISO 639-1 code)
     */
    data class SpokenLanguage(
        val name: String,
        val languageCode: String,
    )
}