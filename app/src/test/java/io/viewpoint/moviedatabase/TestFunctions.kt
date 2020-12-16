package io.viewpoint.moviedatabase

import kotlinx.coroutines.delay

suspend fun tryWithDelay(
    tryCount: Int = 10,
    delay: Long = 200,
    breakPredicate: () -> Boolean
) {
    var repeatCount = tryCount
    while (repeatCount > 0) {
        if (breakPredicate()) {
            break
        }
        repeatCount--
        delay(delay)
    }
}