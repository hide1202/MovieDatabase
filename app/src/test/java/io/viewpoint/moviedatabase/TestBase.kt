package io.viewpoint.moviedatabase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.rules.TestRule

abstract class TestBase {
    @JvmField
    @Rule
    val rule: TestRule = InstantTaskExecutorRule()

    companion object {
        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            Dispatchers.setMain(TestCoroutineDispatcher())
        }
    }
}