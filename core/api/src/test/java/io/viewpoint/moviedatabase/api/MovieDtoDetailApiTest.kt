package io.viewpoint.moviedatabase.api

import io.viewpoint.moviedatabase.api.util.MockResponseReader
import junit.framework.Assert.fail
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isTrue

class MovieDtoDetailApiTest : ApiTest() {
    private val api by lazy {
        MovieDatabaseApi.Builder()
            .apply {
                url = mockServer.url("/").toString()
                apiKey = "test"
            }.build()
    }

    @Test
    fun `watch provider api can parse map`() = withMockResponse(
        MockResponse()
            .setResponseCode(200)
            .setBody(MockResponseReader.fromFile("responses/movie-details/watch-providers.json"))
    ) {
        val api = api.get<MovieDetailApi>()
        val result = runCatching {
            api.getWatchProviders(557)
        }

        expectThat(result.isSuccess).isTrue()
        result
            .onSuccess {
                expectThat(it.results) {
                    get { get("US")?.link }
                        .isNotNull()
                        .isEqualTo("https://www.themoviedb.org/movie/557-spider-man/watch?locale=US")
                }
            }
            .onFailure {
                fail("response must have a successful response")
            }
    }
}