package io.viewpoint.moviedatabase.platform.receiver

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.viewpoint.moviedatabase.api.MovieDatabaseApi
import io.viewpoint.moviedatabase.domain.PreferencesKeys
import io.viewpoint.moviedatabase.domain.preferences.PreferencesService

@HiltWorker
class LocaleChangeWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val preferencesService: PreferencesService
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        MovieDatabaseApi.language =
            preferencesService.getValueWithDefault(PreferencesKeys.SELECTED_LANGUAGE_ISO)
        return Result.success()
    }
}