package io.viewpoint.moviedatabase.api.util

object MockResponseReader {
    fun fromFile(fileName: String): String =
        ClassLoader::class.java.getResourceAsStream(fileName).use {
            String(it?.readBytes() ?: ByteArray(0))
        }
}