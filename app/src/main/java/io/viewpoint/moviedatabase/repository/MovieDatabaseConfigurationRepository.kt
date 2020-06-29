package io.viewpoint.moviedatabase.repository

import arrow.core.*
import arrow.fx.IO
import arrow.fx.extensions.fx
import io.viewpoint.moviedatabase.api.ConfigurationApi
import io.viewpoint.moviedatabase.api.model.ConfigurationResponse
import javax.inject.Inject

class MovieDatabaseConfigurationRepository @Inject constructor(
    private val configurationApi: ConfigurationApi
) : ConfigurationRepository {
    private var configuration: Option<ConfigurationResponse> = Option.empty()

    private fun cache(configuration: ConfigurationResponse?) {
        if (configuration != null) this.configuration = configuration.some()
    }

    override suspend fun getImageBaseUrl(): Option<String> =
        this.configuration
            .mapNotNull {
                it.images?.base_url
            }
            .let {
                IO.fx {
                    if (it.isDefined()) {
                        it
                    } else {
                        !effect {
                            getConfigurationAndCache()
                                .map { response ->
                                    response.images?.base_url
                                }
                                .orNull()
                                .toOption()
                        }
                    }
                }
            }
            .suspended()

    private suspend fun getConfigurationAndCache(): Either<Throwable, ConfigurationResponse> =
        configurationApi.getConfiguration()
            .attempt()
            .suspended()
            .apply {
                cache(this.orNull())
            }
}