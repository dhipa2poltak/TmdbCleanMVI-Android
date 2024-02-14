package com.dpfht.tmdbcleanmvi.framework.data.datasource.remote.rest

import com.dpfht.tmdbcleanmvi.data.Constants
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RestServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var restService: RestService
    private lateinit var gson: Gson

    @Before
    fun setup() {
        gson = Gson()
        mockWebServer = MockWebServer()

        restService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
            .build().create(RestService::class.java)

        val mockResponse = MockResponse()
        mockWebServer.enqueue(mockResponse.setBody("{}"))
    }

    @Test
    fun `ensure path and parameter(s) in the generated URL are correct when calling getMovieGenre method in RestService`() = runTest {
        restService.getMovieGenre()
        val request = mockWebServer.takeRequest()

        val path = request.path ?: ""
        assertTrue(path.isNotEmpty())

        assertEquals("/3/genre/movie/list", path.substring(0, path.indexOf("?")))
        assertTrue(path.contains("api_key=${Constants.API_KEY}"))
    }

    @Test
    fun `ensure path and parameter(s) in the generated URL are correct when calling getMoviesByGenre method in RestService`() = runTest {
        val genreId = 10
        val page = 1
        restService.getMoviesByGenre(genreId.toString(), page)
        val request = mockWebServer.takeRequest()

        val path = request.path ?: ""
        assertTrue(path.isNotEmpty())

        assertEquals("/3/discover/movie", path.substring(0, path.indexOf("?")))
        assertTrue(path.contains("api_key=${Constants.API_KEY}"))
        assertTrue(path.contains("with_genres=$genreId"))
        assertTrue(path.contains("page=$page"))
    }

    @Test
    fun `ensure path and parameter(s) in the generated URL are correct when calling getMovieDetail method in RestService`() = runTest {
        val movieId = 101
        restService.getMovieDetail(movieId)
        val request = mockWebServer.takeRequest()

        val path = request.path ?: ""
        assertTrue(path.isNotEmpty())

        assertEquals("/3/movie/${movieId}", path.substring(0, path.indexOf("?")))
        assertTrue(path.contains("api_key=${Constants.API_KEY}"))
    }

    @Test
    fun `ensure path and parameter(s) in the generated URL are correct when calling getMovieReviews method in RestService`() = runTest {
        val movieId = 101
        val page = 1
        restService.getMovieReviews(movieId, page)
        val request = mockWebServer.takeRequest()

        val path = request.path ?: ""
        assertTrue(path.isNotEmpty())

        assertEquals("/3/movie/${movieId}/reviews", path.substring(0, path.indexOf("?")))
        assertTrue(path.contains("api_key=${Constants.API_KEY}"))
        assertTrue(path.contains("page=$page"))
    }

    @Test
    fun `ensure path and parameter(s) in the generated URL are correct when calling getMovieTrailers method in RestService`() = runTest {
        val movieId = 101
        restService.getMovieTrailers(movieId)
        val request = mockWebServer.takeRequest()

        val path = request.path ?: ""
        assertTrue(path.isNotEmpty())

        assertEquals("/3/movie/${movieId}/videos", path.substring(0, path.indexOf("?")))
        assertTrue(path.contains("api_key=${Constants.API_KEY}"))
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}
