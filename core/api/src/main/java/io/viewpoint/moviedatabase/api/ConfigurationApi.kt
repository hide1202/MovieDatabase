package io.viewpoint.moviedatabase.api

import io.viewpoint.moviedatabase.api.dto.ConfigurationLanguageDto
import io.viewpoint.moviedatabase.api.dto.ConfigurationResponse
import retrofit2.http.GET

interface ConfigurationApi {
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse

    @GET("configuration/languages")
    suspend fun getSupportedLanguages(): List<ConfigurationLanguageDto>
}