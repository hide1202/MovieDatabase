package io.viewpoint.moviedatabase.api

import io.viewpoint.moviedatabase.model.api.ConfigurationLanguage
import io.viewpoint.moviedatabase.model.api.ConfigurationResponse
import retrofit2.http.GET

interface ConfigurationApi {
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse

    @GET("configuration/languages")
    suspend fun getSupportedLanguages(): List<ConfigurationLanguage>
}