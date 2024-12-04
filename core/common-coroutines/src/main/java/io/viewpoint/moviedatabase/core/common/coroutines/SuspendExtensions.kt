package io.viewpoint.moviedatabase.core.common.coroutines

import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> suspendRunCatching(action: suspend () -> T): Result<T> = try {
    Result.success(action())
} catch (ce: CancellationException) {
    throw ce
} catch (e: Exception) {
    Result.failure(e)
}
