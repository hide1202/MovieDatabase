package io.viewpoint.moviedatabase.model.api

data class MovieListResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)