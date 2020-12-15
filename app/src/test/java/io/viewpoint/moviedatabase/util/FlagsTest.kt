package io.viewpoint.moviedatabase.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FlagsTest {
    private val context: Context by lazy {
        ApplicationProvider.getApplicationContext()
    }

    @Test
    fun loadFlag() {
        val usFlag = Flags.getDrawable(context, "US")
        val unknownFlag = Flags.getDrawable(context, "UNKNOWN")

        assertNotNull(usFlag)
        assertNull(unknownFlag)
    }
}