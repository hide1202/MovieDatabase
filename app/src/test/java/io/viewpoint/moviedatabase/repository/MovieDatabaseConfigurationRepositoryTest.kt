package io.viewpoint.moviedatabase.repository

import io.mockk.spyk
import io.mockk.verify
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository
import io.viewpoint.moviedatabase.domain.repository.MovieDatabaseConfigurationRepository.Companion.SUPPORTED_LANGUAGE_CODES
import io.viewpoint.moviedatabase.test.mock.TestConfigurationApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty
import strikt.assertions.isTrue

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
            expectThat(imageUrl).get { isDefined() }.isTrue()

            val secondImageUrl = repository.getImageBaseUrl()
            expectThat(secondImageUrl).get { isDefined() }.isTrue()

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
            expectThat(languages).isNotEmpty()

            val secondLanguages = repository.getSupportedLanguages()
            expectThat(secondLanguages).isNotEmpty()

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