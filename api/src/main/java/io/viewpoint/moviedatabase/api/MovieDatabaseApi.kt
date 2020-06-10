package io.viewpoint.moviedatabase.api

import io.viewpoint.moviedatabase.api.adapter.ArrowCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieDatabaseApi(private val url: String = BASE_URL) {
    inline fun <reified T> get(): T = get(T::class.java)

    fun <T> get(clazz: Class<T>): T = retrofit.create(clazz)

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(ArrowCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}