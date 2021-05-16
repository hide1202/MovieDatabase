@file:Suppress("unused")

package io.viewpoint.moviedatabase.platform

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import io.viewpoint.moviedatabase.platform.util.Flippers
import javax.inject.Inject


@HiltAndroidApp
open class MovieDatabaseApp : Application(), Configuration.Provider {
    // region HiltWorker
    @Inject
    internal lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
    // endregion

    override fun onCreate() {
        super.onCreate()

        Flippers.initialize(this)
    }
}