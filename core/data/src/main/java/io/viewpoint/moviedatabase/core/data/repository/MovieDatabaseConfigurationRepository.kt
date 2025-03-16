package io.viewpoint.moviedatabase.core.data.repository

import io.viewpoint.moviedatabase.api.ConfigurationApi
import io.viewpoint.moviedatabase.core.common.coroutines.suspendRunCatching
import io.viewpoint.moviedatabase.domain.Languages
import io.viewpoint.moviedatabase.api.dto.ConfigurationLanguageDto
import io.viewpoint.moviedatabase.api.dto.ConfigurationResponse
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import java.util.Optional
import javax.inject.Inject
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull

class MovieDatabaseConfigurationRepository @Inject constructor(
    private val configurationApi: ConfigurationApi
) : ConfigurationRepository {
    private var configuration: Optional<ConfigurationResponse> = Optional.empty()

    private var languages: Optional<List<ConfigurationLanguageDto>> = Optional.empty()

    private fun cache(configuration: ConfigurationResponse?) {
        if (configuration != null) this.configuration = Optional.of(configuration)
    }

    private fun cache(languages: List<ConfigurationLanguageDto>?) {
        if (languages != null) this.languages = Optional.of(languages)
    }

    override suspend fun getImageBaseUrl(): Optional<String> =
        this.configuration
            .map<String> { response ->
                response.baseUrlWithSize
            }
            .let { optional ->
                val url = optional?.getOrNull()
                Optional.ofNullable(if (url != null) {
                    url
                } else {
                    getConfigurationAndCache()
                        .map { response ->
                            response.baseUrlWithSize
                        }
                        .getOrNull()
                })
            }

    override suspend fun getSupportedLanguages(): List<ConfigurationLanguageDto> =
        languages
            .let {
                val languages = it.getOrElse {
                    getSupportedLanguagesAndCache()
                        .getOrElse { emptyList() }
                }
                Languages.SUPPORTED_LANGUAGE_CODES
                    .mapNotNull { locale ->
                        languages.firstOrNull { it.iso_639_1 == locale.language }
                    }
            }

    private val ConfigurationResponse.baseUrlWithSize: String?
        get() {
            val baseUrl = images?.secure_base_url
            val posterSize = images?.poster_sizes?.let { list ->
                val center = (list.size / 2).coerceAtMost(list.lastIndex)
                list.getOrNull(center)
            }
            return if (baseUrl != null && posterSize != null) {
                "${baseUrl.trimEnd('/')}/$posterSize"
            } else null
        }

    private suspend fun getConfigurationAndCache(): Result<ConfigurationResponse> =
        suspendRunCatching {
            configurationApi.getConfiguration()
        }.apply {
            cache(getOrNull())
        }

    private suspend fun getSupportedLanguagesAndCache(): Result<List<ConfigurationLanguageDto>> =
        suspendRunCatching {
            configurationApi.getSupportedLanguages()
        }.apply {
            cache(this.getOrNull())
        }
}