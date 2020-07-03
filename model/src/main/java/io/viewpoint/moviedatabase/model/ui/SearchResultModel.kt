package io.viewpoint.moviedatabase.model.ui

import java.io.Serializable

data class SearchResultModel(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?
) : Serializable
