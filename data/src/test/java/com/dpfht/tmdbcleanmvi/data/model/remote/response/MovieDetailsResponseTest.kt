package com.dpfht.tmdbcleanmvi.data.model.remote.response

import com.dpfht.tmdbcleanmvi.data.helpers.FileReaderHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDetailsResponseTest {

  @Test
  fun `ensure converting MovieDetailsResponse to domain is success`() {
    val str = FileReaderHelper.readFileAsString("MovieDetailsResponse.json")
    assertTrue(str.isNotEmpty())

    val typeToken = object : TypeToken<MovieDetailsResponse>() {}.type
    val response = Gson().fromJson<MovieDetailsResponse>(str, typeToken)
    val entity = response.toDomain()

    assertTrue(entity.title.isNotEmpty())
    assertTrue(entity.imageUrl.isNotEmpty())
    assertTrue(entity.genres.isNotEmpty())
    assertTrue(entity.overview.isNotEmpty())
  }
}
