package io.viewpoint.moviedatabase.repository

import io.mockk.spyk
import io.mockk.verify
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository.Companion.SUPPORTED_LANGUAGE_CODES
import io.viewpoint.moviedatabase.test.mock.TestConfigurationApi
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

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

    @Test
    fun `get supported languages`(): Unit = runBlocking {
        val repository = MovieDatabaseConfigurationRepository(TestConfigurationApi())
        val supportedLanguages = repository.getSupportedLanguages()

        expectThat(supportedLanguages.size).isEqualTo(SUPPORTED_LANGUAGE_CODES.size)
        expectThat(supportedLanguages.map { it.iso_639_1 }).isEqualTo(SUPPORTED_LANGUAGE_CODES.map { it.language })
    }
}