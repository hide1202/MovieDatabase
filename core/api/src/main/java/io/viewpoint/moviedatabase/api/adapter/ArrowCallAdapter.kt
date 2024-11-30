package io.viewpoint.moviedatabase.api.adapter

import arrow.fx.IO
import arrow.fx.extensions.fx
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.lang.reflect.Type

class ArrowCallAdapter<R>(
    private val responseType: Type
) : CallAdapter<R, IO<R>> {
    override fun adapt(call: Call<R>): IO<R> = IO.fx {
        !effect {
            val response = call.awaitResponse()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                body
            } else {
                throw HttpException(response)
            }
        }
    }

    override fun responseType(): Type = responseType
}