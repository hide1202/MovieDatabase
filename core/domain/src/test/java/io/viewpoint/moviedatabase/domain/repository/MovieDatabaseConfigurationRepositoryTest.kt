package io.viewpoint.moviedatabase.domain.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.viewpoint.moviedatabase.api.ConfigurationApi
import io.viewpoint.moviedatabase.domain.Languages.SUPPORTED_LANGUAGE_CODES
import io.viewpoint.moviedatabase.test.mock.TestConfigurationApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty
import strikt.assertions.isTrue

class MovieDatabaseConfigurationRepositoryTest {
    @Test
    fun `repository can cache configuration`() =
        runTest {
            val api = mockk<ConfigurationApi>()
            coEvery { api.getConfiguration() } coAnswers { TestConfigurationApi().getConfiguration() }

            val repository =
                MovieDatabaseConfigurationRepository(
                    api
                )

            val imageUrl = repository.getImageBaseUrl()
            expectThat(imageUrl).get { isPresent }.isTrue()

            val secondImageUrl = repository.getImageBaseUrl()
            expectThat(secondImageUrl).get { isPresent }.isTrue()

            coVerify(exactly = 1) { api.getConfiguration() }
        }

    @Test
    fun `repository can cache languages`() =
        runTest {
            val api = mockk<ConfigurationApi>()
            coEvery { api.getSupportedLanguages() } coAnswers { TestConfigurationApi().getSupportedLanguages() }
            val repository =
                MovieDatabaseConfigurationRepository(
                    api
                )

            val languages = repository.getSupportedLanguages()
            expectThat(languages).isNotEmpty()

            val secondLanguages = repository.getSupportedLanguages()
            expectThat(secondLanguages).isNotEmpty()

            coVerify(exactly = 1) { api.getSupportedLanguages() }
        }

    @Test
    fun `get supported languages`(): Unit = runTest {
        val repository = MovieDatabaseConfigurationRepository(TestConfigurationApi())
        val supportedLanguages = repository.getSupportedLanguages()

        expectThat(supportedLanguages.size).isEqualTo(SUPPORTED_LANGUAGE_CODES.size)
        expectThat(supportedLanguages.map { it.iso_639_1 }).isEqualTo(SUPPORTED_LANGUAGE_CODES.map { it.language })
    }
}