package io.viewpoint.moviedatabase.api.adapter

import arrow.fx.IO
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ArrowCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) !== IO::class.java) {
            return null
        }
        val innerType = getParameterUpperBound(0, returnType as ParameterizedType)
        return ArrowCallAdapter<Type>(innerType)
    }
}