package io.viewpoint.moviedatabase.core.data.repository

import io.viewpoint.moviedatabase.api.MovieDatabaseApi

object MovieDatabaseApiConfiguration {
    fun changeLanguage(language: String) {
        MovieDatabaseApi.language = language
    }
}
