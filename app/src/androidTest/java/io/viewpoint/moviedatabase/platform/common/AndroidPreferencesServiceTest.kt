package io.viewpoint.moviedatabase.platform.common

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.viewpoint.moviedatabase.domain.preferences.PreferenceKey
import io.viewpoint.moviedatabase.test.TestBase
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty

@RunWith(AndroidJUnit4::class)
class AndroidPreferencesServiceTest : TestBase() {
    private val preferencesService =
        AndroidPreferencesService(InstrumentationRegistry.getInstrumentation().context)

    @Test
    fun preferencesCanGetAndSetStringSet() = runBlocking {
        val preferenceKey = PreferenceKey(
            key = "list_test",
            type = TestData::class
        )
        val zero = preferencesService.getValues(preferenceKey) {
            TestData.from(it)
        }
        expectThat(zero).isEmpty()

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
        expectThat(two).hasSize(2)

        preferencesService.putValues(preferenceKey, emptyList()) {
            it.toString()
        }

        val againZero = preferencesService.getValues(preferenceKey) {
            TestData.from(it)
        }
        expectThat(againZero).isEmpty()
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