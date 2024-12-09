@file:Suppress("unused")

package io.viewpoint.moviedatabase.platform

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.Lazy
import dagger.hilt.android.HiltAndroidApp
import io.viewpoint.moviedatabase.platform.util.Flippers
import javax.inject.Inject


@HiltAndroidApp
open class MovieDatabaseApp : Application(), Configuration.Provider, SingletonImageLoader.Factory {
    // region HiltWorker

    @Inject
    internal lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    // endregion

    // region coil

    @Inject
    internal lateinit var imageLoader: Lazy<ImageLoader>

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return imageLoader.get()
    }

    // endregion

    override fun onCreate() {
        super.onCreate()

        Flippers.initialize(this)
    }
}