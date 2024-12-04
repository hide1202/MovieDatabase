package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.model.api.ConfigurationLanguage
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

interface ConfigurationRepository {
    suspend fun getImageBaseUrl(): Optional<String>

    suspend fun getImageUrl(pathSupplier: () -> String?): String? = getImageBaseUrl()
        .filter {
            pathSupplier() != null
        }
        .map { baseUrl ->
            "${baseUrl.trimEnd('/')}/${pathSupplier()?.trimStart('/')}"
        }
        .getOrNull()

    suspend fun getSupportedLanguages(): List<ConfigurationLanguage>
}