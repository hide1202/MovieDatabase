package io.viewpoint.moviedatabase.core.data.repository

import io.viewpoint.moviedatabase.api.MovieDatabaseApi

object MovieDatabaseApiConfiguration {
    val currentLanguage: String?
        get() = MovieDatabaseApi.language

    fun changeLanguage(language: String?) {
        MovieDatabaseApi.language = language
    }
}
