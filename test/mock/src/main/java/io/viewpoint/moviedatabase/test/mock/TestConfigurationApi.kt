package io.viewpoint.moviedatabase.test.mock

import com.squareup.moshi.Types
import io.viewpoint.moviedatabase.api.ConfigurationApi
import io.viewpoint.moviedatabase.model.api.ConfigurationLanguage
import io.viewpoint.moviedatabase.model.api.ConfigurationResponse
import io.viewpoint.moviedatabase.test.common.MoshiReader
import io.viewpoint.moviedatabase.test.common.ResponseReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TestConfigurationApi : ConfigurationApi {
    override suspend fun getConfiguration(): ConfigurationResponse {
        return withContext(Dispatchers.IO) {
            ResponseReader.jsonFromFileAsync(
                "responses/configuration-results.json",
                MoshiReader.moshi.adapter(ConfigurationResponse::class.java)
            )
        }
    }

    override suspend fun getSupportedLanguages(): List<ConfigurationLanguage> {
        return withContext(Dispatchers.IO) {
            val type =
                Types.newParameterizedType(List::class.java, ConfigurationLanguage::class.java)
            ResponseReader.jsonFromFileAsync(
                "responses/languages-results.json",
                MoshiReader.moshi.adapter<List<ConfigurationLanguage>>(type)
            )
        }
    }
}