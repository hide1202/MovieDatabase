package io.viewpoint.moviedatabase.model.api

data class MoviePopularResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)