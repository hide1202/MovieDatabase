package io.viewpoint.moviedatabase.test.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.squareup.moshi.Types
import io.viewpoint.moviedatabase.api.ConfigurationApi
import io.viewpoint.moviedatabase.model.api.ConfigurationLanguage
import io.viewpoint.moviedatabase.model.api.ConfigurationResponse

class TestConfigurationApi : ConfigurationApi {
    override fun getConfiguration(): IO<ConfigurationResponse> =
        IO.fx {
            !effect {
                io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                    "responses/configuration-results.json",
                    io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter(ConfigurationResponse::class.java)
                )
            }
        }

    override fun getSupportedLanguages(): IO<List<ConfigurationLanguage>> =
        IO.fx {
            !effect {
                val type =
                    Types.newParameterizedType(List::class.java, ConfigurationLanguage::class.java)
                io.viewpoint.moviedatabase.test.ResponseReader.jsonFromFileAsync(
                    "responses/languages-results.json",
                    io.viewpoint.moviedatabase.test.MoshiReader.moshi.adapter<List<ConfigurationLanguage>>(type)
                )
            }
        }
}