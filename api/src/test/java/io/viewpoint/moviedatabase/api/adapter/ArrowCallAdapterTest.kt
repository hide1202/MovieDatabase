package io.viewpoint.moviedatabase.api.adapter

import arrow.core.Either
import arrow.fx.IO
import io.viewpoint.moviedatabase.api.ApiTest
import junit.framework.Assert.*
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET

class ArrowCallAdapterTest : ApiTest() {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addCallAdapterFactory(ArrowCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    data class TestResponse(
        val name: String,
        val age: Int
    )

    interface TestApi {
        @GET("/")
        fun test(): IO<TestResponse>
    }

    @Test
    fun successResponse() = withMockResponse(
        MockResponse().setBody(
            """
            {
                "name": "test",
                "age" : 30
            }
        """.trimIndent()
        )
    ) {
        val api = retrofit.create<TestApi>()
        val response = api.test()
            .attempt()
            .suspended()
        assertTrue(response.isRight())
        when (response) {
            is Either.Left -> fail()
            is Either.Right -> {
                assertEquals("test", response.b.name)
                assertEquals(30, response.b.age)
            }
        }
    }
}