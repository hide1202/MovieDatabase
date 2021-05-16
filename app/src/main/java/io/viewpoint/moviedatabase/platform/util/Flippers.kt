package io.viewpoint.moviedatabase.platform.util

import android.content.Context
import okhttp3.Interceptor

interface Flippers {
    fun initialize(context: Context)

    fun networkInterceptor(): Interceptor?

    companion object : Flippers by FlipperImpl()
}