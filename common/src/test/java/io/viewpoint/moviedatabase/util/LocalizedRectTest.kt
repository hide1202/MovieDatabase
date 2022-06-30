package io.viewpoint.moviedatabase.util

import android.graphics.Rect
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@RunWith(RobolectricTestRunner::class)
class LocalizedRectTest {
    @Test
    fun localizedRectRTLTest() {
        val rect = LocalizedRect(
            start = 0,
            top = 0,
            end = 100,
            bottom = 100
        )
        expectThat(rect.toRect(isRtl = false)).isEqualTo(Rect(0, 0, 100, 100))
        expectThat(rect.toRect(isRtl = true)).isEqualTo(Rect(100, 0, 0, 100))
    }
}