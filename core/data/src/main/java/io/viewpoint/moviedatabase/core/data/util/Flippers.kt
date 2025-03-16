package io.viewpoint.moviedatabase.core.data.util

import android.content.Context
import io.viewpoint.moviedatabase.platform.util.FlipperImpl
import okhttp3.Interceptor

interface Flippers {
    fun initialize(context: Context)

    fun networkInterceptor(): Interceptor?

    companion object : Flippers by FlipperImpl()
}