package io.viewpoint.moviedatabase.util

import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ResponseReader {
    fun blockingFromFile(fileName: String): String =
        ClassLoader::class.java.getResourceAsStream(fileName).use {
            String(it?.readBytes() ?: ByteArray(0))
        }

    suspend fun fromFileAsync(fileName: String): String =
        withContext(Dispatchers.IO) {
            ClassLoader::class.java.getResourceAsStream(fileName).use {
                String(it?.readBytes() ?: ByteArray(0))
            }
        }

    inline fun <reified T> blockingJsonFromFile(fileName: String, adapter: JsonAdapter<T>): T =
        MoshiReader.read(blockingFromFile(fileName), adapter)

    suspend inline fun <reified T> jsonFromFileAsync(fileName: String, adapter: JsonAdapter<T>): T {
        val json = fromFileAsync(fileName)
        return MoshiReader.read(json, adapter)
    }
}