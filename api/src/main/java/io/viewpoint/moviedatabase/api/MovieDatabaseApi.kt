package io.viewpoint.moviedatabase.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.viewpoint.moviedatabase.api.adapter.ArrowCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class MovieDatabaseApi private constructor(
    builder: Builder
) {
    private val debugLog: ((String) -> Unit)? = builder.debugLog
    private val apiKey: String = requireNotNull(builder.apiKey) {
        "apiKey MUST be not null"
    }

    inline fun <reified T> get(): T = get(T::class.java)

    fun <T> get(clazz: Class<T>): T = retrofit.create(clazz)

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(builder.url)
            .client(OkHttpClient.Builder()
                .apply {
                    val logMethod = debugLog
                    if (logMethod != null) {
                        addInterceptor(HttpLoggingInterceptor {
                            logMethod(it)
                        }.apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                    }

                    addInterceptor { chain ->
                        val request = chain.request()

                        chain.proceed(
                            request
                                .newBuilder()
                                .url(
                                    request.url()
                                        .newBuilder()
                                        .addQueryParameter("api_key", apiKey)
                                        .addQueryParameter("region", getRegion())
                                        .let { builder ->
                                            if (language != null) {
                                                builder.addQueryParameter("language", language)
                                            } else builder
                                        }
                                        .build()
                                )
                                .build()
                        )
                    }
                }
                .build())
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

    private fun getRegion(): String? = Locale.getDefault().country?.substring(0, 2)

    class Builder {
        var url: String = BASE_URL
        var debugLog: ((String) -> Unit)? = null
        var apiKey: String? = null

        fun build(): MovieDatabaseApi =
            MovieDatabaseApi(this)
    }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"

        var language: String? = null
    }
}