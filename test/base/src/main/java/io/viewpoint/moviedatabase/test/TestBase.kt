package io.viewpoint.moviedatabase.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.viewpoint.moviedatabase.test.rules.TestCoroutineRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule

abstract class TestBase {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    protected val testScope = TestCoroutineScope()
}