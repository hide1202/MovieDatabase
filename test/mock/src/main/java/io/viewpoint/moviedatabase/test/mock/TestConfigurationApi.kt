package io.viewpoint.moviedatabase.test.mock

import arrow.fx.IO
import arrow.fx.extensions.fx
import com.squareup.moshi.Types
import io.viewpoint.moviedatabase.api.ConfigurationApi
import io.viewpoint.moviedatabase.model.api.ConfigurationLanguage
import io.viewpoint.moviedatabase.model.api.ConfigurationResponse
import io.viewpoint.moviedatabase.test.common.MoshiReader
import io.viewpoint.moviedatabase.test.common.ResponseReader

class TestConfigurationApi : ConfigurationApi {
    override fun getConfiguration(): IO<ConfigurationResponse> =
        IO.fx {
            !effect {
                ResponseReader.jsonFromFileAsync(
                    "responses/configuration-results.json",
                    MoshiReader.moshi.adapter(ConfigurationResponse::class.java)
                )
            }
        }

    override fun getSupportedLanguages(): IO<List<ConfigurationLanguage>> =
        IO.fx {
            !effect {
                val type =
                    Types.newParameterizedType(List::class.java, ConfigurationLanguage::class.java)
                ResponseReader.jsonFromFileAsync(
                    "responses/languages-results.json",
                    MoshiReader.moshi.adapter<List<ConfigurationLanguage>>(type)
                )
            }
        }
}