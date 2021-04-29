package io.viewpoint.moviedatabase.domain.repository

import arrow.core.*
import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.ConfigurationApi
import io.viewpoint.moviedatabase.model.api.ConfigurationLanguage
import io.viewpoint.moviedatabase.model.api.ConfigurationResponse
import java.util.*
import javax.inject.Inject

class MovieDatabaseConfigurationRepository @Inject constructor(
    private val configurationApi: ConfigurationApi
) : ConfigurationRepository {
    private var configuration: Option<ConfigurationResponse> = Option.empty()

    private var languages: Option<List<ConfigurationLanguage>> = Option.empty()

    private fun cache(configuration: ConfigurationResponse?) {
        if (configuration != null) this.configuration = configuration.some()
    }

    private fun cache(languages: List<ConfigurationLanguage>?) {
        if (languages != null) this.languages = languages.some()
    }

    override suspend fun getImageBaseUrl(): Option<String> =
        this.configuration
            .mapNotNull { response ->
                response.baseUrlWithSize
            }
            .let {
                IO.fx {
                    if (it.isDefined()) {
                        it
                    } else {
                        !effect {
                            getConfigurationAndCache()
                                .map { response ->
                                    response.baseUrlWithSize
                                }
                                .orNull()
                                .toOption()
                        }
                    }
                }
            }
            .suspended()

    override suspend fun getSupportedLanguages(): List<ConfigurationLanguage> =
        languages
            .let {
                IO.fx {
                    if (it.isDefined()) {
                        it.getOrElse { emptyList() }
                    } else {
                        !effect {
                            getSupportedLanguagesAndCache()
                                .getOrElse { emptyList() }
                        }
                    }
                }
            }
            .map { languages ->
                SUPPORTED_LANGUAGE_CODES
                    .mapNotNull { locale ->
                        languages.firstOrNull { it.iso_639_1 == locale.language }
                    }
            }
            .suspended()

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

    private suspend fun getConfigurationAndCache(): Either<Throwable, ConfigurationResponse> =
        configurationApi.getConfiguration()
            .attempt()
            .suspended()
            .apply {
                cache(this.orNull())
            }

    private suspend fun getSupportedLanguagesAndCache(): Either<Throwable, List<ConfigurationLanguage>> =
        configurationApi.getSupportedLanguages()
            .attempt()
            .suspended()
            .apply {
                cache(this.orNull())
            }

    companion object {
        val SUPPORTED_LANGUAGE_CODES = listOf(Locale.ENGLISH, Locale.KOREAN, Locale.JAPANESE)
    }
}