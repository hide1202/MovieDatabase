package io.viewpoint.moviedatabase.platform.util

import android.content.Context
import okhttp3.Interceptor

class FlipperImpl : Flippers {

    override fun initialize(context: Context) = Unit

    override fun networkInterceptor(): Interceptor? = null
}