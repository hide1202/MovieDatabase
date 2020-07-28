package io.viewpoint.moviedatabase.platform.common

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.viewpoint.moviedatabase.domain.preferences.PreferenceKey
import junit.framework.Assert.assertEquals
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidPreferencesServiceTest {
    private val preferencesService =
        AndroidPreferencesService(InstrumentationRegistry.getInstrumentation().context)

    @Test
    fun preferencesCanGetAndSetStringSet() {
        val preferenceKey = PreferenceKey(
            key = "list_test",
            type = TestData::class
        )
        val zero = preferencesService.getValues(preferenceKey) {
            TestData.from(it)
        }
        assertEquals(0, zero.size)

        preferencesService.putValues(
            preferenceKey, listOf(
                TestData(name = "test", age = 23),
                TestData(name = "test2", age = 25)
            )
        ) {
            it.toString()
        }

        val two = preferencesService.getValues(preferenceKey) {
            TestData.from(it)
        }
        assertEquals(2, two.size)

        preferencesService.putValues(preferenceKey, emptyList()) {
            it.toString()
        }

        val againZero = preferencesService.getValues(preferenceKey) {
            TestData.from(it)
        }
        assertEquals(0, againZero.size)
    }

    data class TestData(
        val name: String,
        val age: Int
    ) {
        override fun toString(): String {
            return JSONObject().apply {
                put("name", name)
                put("age", age)
            }.toString()
        }

        companion object {
            fun from(json: String): TestData {
                return JSONObject(json).let {
                    TestData(
                        name = it.getString("name"),
                        age = it.getInt("age")
                    )
                }

            }
        }
    }
}