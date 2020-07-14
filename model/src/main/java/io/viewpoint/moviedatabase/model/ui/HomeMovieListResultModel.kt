package io.viewpoint.moviedatabase.model.ui

import java.io.Serializable

data class HomeMovieListResultModel(
    val title: String,
    val posterUrl: String?
) : Serializable