package io.viewpoint.moviedatabase.test

import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

object ResponseReader {
    suspend fun fromFileAsync(fileName: String): String =
        withContext(Dispatchers.IO) {
            blockingFromFile(fileName)
        }

    fun blockingFromFile(fileName: String): String =
        getResourceAsStream(fileName).use {
            String(it?.readBytes() ?: ByteArray(0))
        }

    private fun getResourceAsStream(fileName: String): InputStream? =
        Thread.currentThread().contextClassLoader?.getResourceAsStream(fileName)

    suspend inline fun <reified T> jsonFromFileAsync(fileName: String, adapter: JsonAdapter<T>): T {
        val json = fromFileAsync(fileName)
        return MoshiReader.read(json, adapter)
    }
}