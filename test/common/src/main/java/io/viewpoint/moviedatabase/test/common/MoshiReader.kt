package io.viewpoint.moviedatabase.test.common

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiReader {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    inline fun <reified T> read(json: String, adapter: JsonAdapter<T>): T =
        adapter.fromJson(json) as T
}