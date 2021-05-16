package io.viewpoint.moviedatabase.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.viewpoint.moviedatabase.TestApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import strikt.api.expectThat
import strikt.assertions.isNotNull
import strikt.assertions.isNull

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class FlagsTest {
    private val context: Context by lazy {
        ApplicationProvider.getApplicationContext()
    }

    @Test
    fun loadFlag() {
        val usFlag = Flags.getDrawable(context, "US")
        val unknownFlag = Flags.getDrawable(context, "UNKNOWN")

        expectThat(usFlag).isNotNull()
        expectThat(unknownFlag).isNull()
    }
}