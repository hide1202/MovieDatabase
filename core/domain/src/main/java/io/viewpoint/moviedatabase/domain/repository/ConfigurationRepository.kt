package io.viewpoint.moviedatabase.domain.repository

import arrow.core.Option
import io.viewpoint.moviedatabase.model.api.ConfigurationLanguage

interface ConfigurationRepository {
    suspend fun getImageBaseUrl(): Option<String>

    suspend fun getImageUrl(pathSupplier: () -> String?): String? = getImageBaseUrl()
        .filter {
            pathSupplier() != null
        }
        .map { baseUrl ->
            "${baseUrl.trimEnd('/')}/${pathSupplier()?.trimStart('/')}"
        }
        .orNull()

    suspend fun getSupportedLanguages(): List<ConfigurationLanguage>
}