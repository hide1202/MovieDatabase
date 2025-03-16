package io.viewpoint.moviedatabase.core.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.spyk
import io.viewpoint.moviedatabase.api.SearchApi
import io.viewpoint.moviedatabase.api.dto.MovieDto
import io.viewpoint.moviedatabase.model.common.PagingResult
import io.viewpoint.moviedatabase.test.mock.TestSearchApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty

class MovieDtoDatabaseSearchRepositoryTest {
    @Test
    fun `repository can search a keyword`() =
        runBlocking {
            val api = spyk<SearchApi>()
            coEvery {
                api.searchMovie(any(), any())
            } coAnswers {
                TestSearchApi().searchMovie(
                    "",
                    1
                )
            }

            val repository =
                MovieDatabaseSearchRepository(
                    api
                )

            val result: PagingResult<Int, MovieDto> = repository.searchKeyword(keyword = "", page = 1)

            expectThat(result)
                .isA<PagingResult.Success<Int, MovieDto>>()
                .get { this.data }
                .isNotEmpty()

            expectThat(result)
                .isA<PagingResult.Success<Int, MovieDto>>()
                .get { this.nextKey }
                .isEqualTo(2)

            coVerify(exactly = 1) { api.searchMovie(any(), any()) }
        }
}