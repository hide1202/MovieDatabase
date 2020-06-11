package io.viewpoint.moviedatabase.api.model

data class MovieSearchResponse(
    val page: Int,
    val results: List<MovieSearchResult>,
    val total_pages: Int,
    val total_results: Int
) {
    data class MovieSearchResult(
        val adult: Boolean,
        val backdrop_path: String?,
        val genre_ids: List<Int>,
        val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
    )
}