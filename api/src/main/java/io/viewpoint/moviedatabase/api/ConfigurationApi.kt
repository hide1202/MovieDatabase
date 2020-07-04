package io.viewpoint.moviedatabase.api

import arrow.fx.IO
import io.viewpoint.moviedatabase.model.api.ConfigurationLanguage
import io.viewpoint.moviedatabase.model.api.ConfigurationResponse
import retrofit2.http.GET

interface ConfigurationApi {
    @GET("configuration")
    fun getConfiguration(): IO<ConfigurationResponse>

    @GET("configuration/languages")
    fun getSupportedLanguages(): IO<List<ConfigurationLanguage>>
}