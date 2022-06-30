package io.viewpoint.moviedatabase.domain.repository

import io.viewpoint.moviedatabase.test.mock.TestMovieDetailApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isNotEmpty
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class MovieDatabaseMovieDetailRepositoryTest {
    private val repository = MovieDatabaseMovieDetailRepository(TestMovieDetailApi())

    @Test
    fun getMovieDetailTest() = runBlocking {
        expectThat(repository.getMovieDetail(TestMovieDetailApi.VALID_ID)) {
            isNotNull()
        }
        expectThat(repository.getMovieDetail(558)) {
            isNull()
        }
    }

    @Test
    fun getCreditsTest() = runBlocking {
        expectThat(repository.getCredits(TestMovieDetailApi.VALID_ID)) {
            isNotEmpty()
        }
        expectThat(repository.getCredits(558)) {
            isEmpty()
        }
    }

    @Test
    fun getKeywordsTest() = runBlocking {
        expectThat(repository.getKeywords(TestMovieDetailApi.VALID_ID)) {
            isNotEmpty()
        }
        expectThat(repository.getKeywords(558)) {
            isEmpty()
        }
    }

    @Test
    fun getRecommendationsTest() = runBlocking {
        expectThat(repository.getRecommendations(TestMovieDetailApi.VALID_ID)) {
            isNotEmpty()
        }
        expectThat(repository.getRecommendations(-1)) {
            isEmpty()
        }
    }

    @Test
    fun getWatchProvidersTest() = runBlocking {
        expectThat(repository.getWatchProviders(TestMovieDetailApi.VALID_ID, "KR")) {
            isNotNull()
        }
        expectThat(repository.getWatchProviders(TestMovieDetailApi.VALID_ID, "XX")) {
            isNull()
        }
        expectThat(repository.getWatchProviders(558, "KR")) {
            isNull()
        }
    }
}