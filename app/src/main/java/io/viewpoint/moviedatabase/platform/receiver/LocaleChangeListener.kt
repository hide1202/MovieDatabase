package io.viewpoint.moviedatabase.platform.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocaleChangeListener : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_LOCALE_CHANGED) {
            WorkManager.getInstance(context)
                .enqueue(
                    OneTimeWorkRequestBuilder<LocaleChangeWorker>()
                        .build()
                )
        }
    }
}
