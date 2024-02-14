package com.dpfht.tmdbcleanmvi.data.model.remote.response

import com.dpfht.tmdbcleanmvi.data.helpers.FileReaderHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DiscoverMovieByGenreResponseTest {

  @Test
  fun `ensure converting DiscoverMovieByGenreResponse to domain is success`() {
    val str = FileReaderHelper.readFileAsString("DiscoverMovieByGenreResponse.json")
    assertTrue(str.isNotEmpty())

    val typeToken = object : TypeToken<DiscoverMovieByGenreResponse>() {}.type
    val response = Gson().fromJson<DiscoverMovieByGenreResponse>(str, typeToken)
    val entity = response.toDomain()

    assertTrue(entity.results.size == 20)
  }
}
