package io.viewpoint.moviedatabase.model.ui

import java.io.Serializable

data class SearchResultModel(
    val id: Int,
    val title: String,
    val originalTitle: String?,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val vote: Double,
    val releaseDate: String?
) : Serializable
