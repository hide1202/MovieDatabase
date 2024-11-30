package io.viewpoint.moviedatabase.api.adapter

import arrow.core.Either
import arrow.fx.IO
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.viewpoint.moviedatabase.api.ApiTest
import junit.framework.Assert.fail
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue

class ArrowCallAdapterTest : ApiTest() {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addCallAdapterFactory(ArrowCallAdapterFactory())
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
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
        expectThat(response.isRight()).isTrue()
        when (response) {
            is Either.Left -> fail()
            is Either.Right -> {
                expectThat(response.b) {
                    get { name }.isEqualTo("test")
                    get { age }.isEqualTo(30)
                }
            }
        }
    }
}