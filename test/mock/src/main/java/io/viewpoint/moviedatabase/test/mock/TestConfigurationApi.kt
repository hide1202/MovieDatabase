package io.viewpoint.moviedatabase.test.mock

import com.squareup.moshi.Types
import io.viewpoint.moviedatabase.api.ConfigurationApi
import io.viewpoint.moviedatabase.api.dto.ConfigurationLanguageDto
import io.viewpoint.moviedatabase.api.dto.ConfigurationResponse
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

    override suspend fun getSupportedLanguages(): List<ConfigurationLanguageDto> {
        return withContext(Dispatchers.IO) {
            val type =
                Types.newParameterizedType(List::class.java, ConfigurationLanguageDto::class.java)
            ResponseReader.jsonFromFileAsync(
                "responses/languages-results.json",
                MoshiReader.moshi.adapter<List<ConfigurationLanguageDto>>(type)
            )
        }
    }
}