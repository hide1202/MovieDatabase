package io.viewpoint.moviedatabase.platform.ui.search.model

data class SearchResultModel(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?
)
