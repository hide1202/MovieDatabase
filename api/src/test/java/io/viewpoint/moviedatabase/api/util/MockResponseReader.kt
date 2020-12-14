package io.viewpoint.moviedatabase.api.util

import java.io.InputStream

object MockResponseReader {
    fun fromFile(fileName: String): String =
        getResourceAsStream(fileName).use {
            String(it?.readBytes() ?: ByteArray(0))
        }

    private fun getResourceAsStream(fileName: String): InputStream? =
        Thread.currentThread().contextClassLoader?.getResourceAsStream(fileName)
}