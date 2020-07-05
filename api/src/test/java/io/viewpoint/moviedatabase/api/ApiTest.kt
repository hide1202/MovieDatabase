package io.viewpoint.moviedatabase.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import java.util.concurrent.TimeUnit


abstract class ApiTest {
    protected val mockServer = MockWebServer()

    @Before
    fun loadWebServer() {
        mockServer.start()
        TimeUnit.SECONDS.sleep(2)

        println("start webserver")
    }

    @After
    fun shutdownWebServer() {
        mockServer.shutdown()
        TimeUnit.SECONDS.sleep(2)
        println("shutdown webserver")
    }

    fun withMockResponse(mockResponse: MockResponse, action: suspend () -> Unit) {
        mockServer.enqueue(mockResponse)
        runBlocking {
            action()
        }
    }
}