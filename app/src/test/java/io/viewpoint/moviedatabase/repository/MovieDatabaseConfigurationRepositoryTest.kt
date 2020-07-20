package io.viewpoint.moviedatabase.repository

import io.mockk.spyk
import io.mockk.verify
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.mock.TestConfigurationApi
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MovieDatabaseConfigurationRepositoryTest {
    @Test
    fun `repository can cache configuration`() =
        runBlocking {
            val api = spyk<TestConfigurationApi>()
            val repository =
                MovieDatabaseConfigurationRepository(
                    api
                )

            val imageUrl = repository.getImageBaseUrl()
            assertTrue(imageUrl.isDefined())

            val secondImageUrl = repository.getImageBaseUrl()
            assertTrue(secondImageUrl.isDefined())

            verify(exactly = 1) { api.getConfiguration() }
        }

    @Test
    fun `repository can cache languages`() =
        runBlocking {
            val api = spyk<TestConfigurationApi>()
            val repository =
                MovieDatabaseConfigurationRepository(
                    api
                )

            val languages = repository.getSupportedLanguages()
            assertTrue(languages.isNotEmpty())

            val secondLanguages = repository.getSupportedLanguages()
            assertTrue(secondLanguages.isNotEmpty())

            verify(exactly = 1) { api.getSupportedLanguages() }
        }
}