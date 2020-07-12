package io.viewpoint.moviedatabase

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.AfterClass
import org.junit.BeforeClass

abstract class TestBase {
    companion object {
        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            Dispatchers.setMain(TestCoroutineDispatcher())

            ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()

                override fun isMainThread(): Boolean = true

                override fun postToMainThread(runnable: Runnable) = runnable.run()
            })
        }

        @JvmStatic
        @AfterClass
        fun tearDownClass() {
            ArchTaskExecutor.getInstance().setDelegate(null)
        }
    }
}