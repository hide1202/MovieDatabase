package io.viewpoint.moviedatabase.api

import arrow.core.Either
import io.viewpoint.moviedatabase.api.util.MockResponseReader
import junit.framework.Assert.*
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import retrofit2.HttpException

class SearchApiTest : ApiTest() {
    private val api by lazy {
        MovieDatabaseApi(mockServer.url("/").toString())
    }

    @Test
    fun `success to search movie`() =
        withMockResponse(
            MockResponse()
                .setBody(MockResponseReader.fromFile("/responses/search/spider-man.json"))
        ) {
            val api = api.get<SearchApi>()

            val response = api.searchMovie("")
                .suspended()
            assertEquals(1, response.page)
            assertEquals(56, response.total_results)
        }

    @Test
    fun `fail to search movie because of authorization`() =
        withMockResponse(
            MockResponse()
                .setResponseCode(401)
                .setBody(MockResponseReader.fromFile("/responses/search/spider-man.json"))
        ) {
            val api = api.get<SearchApi>()
            val either = api.searchMovie("")
                .attempt()
                .suspended()

            assertTrue(either.isLeft())
            when (either) {
                is Either.Left -> {
                    val exception = either.a
                    if (exception is HttpException) {
                        assertTrue(exception.code() == 401)
                    } else {
                        fail()
                    }
                }
                is Either.Right -> fail()
            }
        }
}