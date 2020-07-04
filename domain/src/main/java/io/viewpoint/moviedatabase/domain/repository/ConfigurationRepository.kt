package io.viewpoint.moviedatabase.domain.repository

import arrow.core.Option
import io.viewpoint.moviedatabase.model.api.ConfigurationLanguage

interface ConfigurationRepository {
    suspend fun getImageBaseUrl(): Option<String>

    suspend fun getSupportedLanguages(): List<ConfigurationLanguage>
}