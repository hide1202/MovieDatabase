package io.viewpoint.moviedatabase.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.viewpoint.moviedatabase.test.rules.TestCoroutineRule
import org.junit.Rule

abstract class TestBase {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()
}