package io.viewpoint.moviedatabase.domain.search

import arrow.core.Option
import arrow.core.some
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.model.api.ConfigurationLanguage
import io.viewpoint.moviedatabase.model.api.Movie
import junit.framework.Assert.fail
import kotlinx.coroutines.runBlocking
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SearchResultMapperTest {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Test
    fun mapTest() = runBlocking {
        // Arrange
        val response =
            moshi.adapter(Movie::class.java)
                .fromJson(RESULT_JSON)

        if (response == null) {
            fail()
            return@runBlocking
        }

        val expectedImageBaseUrl = "http://image.tmdb.org/t/p/"
        val mapper = SearchResultMapperProvider(object : ConfigurationRepository {
            override suspend fun getImageBaseUrl(): Option<String> =
                expectedImageBaseUrl.some()

            override suspend fun getSupportedLanguages(): List<ConfigurationLanguage> =
                emptyList()
        })

        expectThat(mapper.mapperFromMovie.map(response).posterUrl)
            .isEqualTo("${expectedImageBaseUrl}eXbCqcUsUDUq2qqGmU5i20S0tjo.jpg")
    }

    companion object {
        const val RESULT_JSON = """
            {
               "popularity":41.281,
               "id":557,
               "video":false,
               "vote_count":12015,
               "vote_average":7.1,
               "title":"스파이더맨",
               "release_date":"2002-05-01",
               "original_language":"en",
               "original_title":"Spider-Man",
               "genre_ids":[
                  14,
                  28
               ],
               "backdrop_path":"/jHxCeXnSchAuwHnmVatTgqMYdX8.jpg",
               "adult":false,
               "overview":"설명입니다",
               "poster_path":"/eXbCqcUsUDUq2qqGmU5i20S0tjo.jpg"
            }
        """
    }
}
