package io.viewpoint.moviedatabase.platform.util

import io.viewpoint.moviedatabase.BuildConfig
import timber.log.Timber

object DebugLogger {
    private const val TAG = "debug-log"

    fun d(msg: String, vararg params: Any) {
        if (BuildConfig.DEBUG) {
            Timber.tag(TAG).d(msg, params)
        }
    }
}