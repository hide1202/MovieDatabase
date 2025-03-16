package io.viewpoint.moviedatabase.platform.util

import android.content.Context
import okhttp3.Interceptor
import io.viewpoint.moviedatabase.core.data.util.Flippers

class FlipperImpl : Flippers {

    override fun initialize(context: Context) = Unit

    override fun networkInterceptor(): Interceptor? = null
}