package com.dpfht.tmdbcleanmvi.data.model.remote.response

import com.dpfht.tmdbcleanmvi.data.helpers.FileReaderHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReviewResponseTest {

  @Test
  fun `ensure converting ReviewResponse to domain is success`() {
    val str = FileReaderHelper.readFileAsString("ReviewResponse.json")
    assertTrue(str.isNotEmpty())

    val typeToken = object : TypeToken<ReviewResponse>() {}.type
    val response = Gson().fromJson<ReviewResponse>(str, typeToken)
    val entity = response.toDomain()

    assertTrue(entity.results.size == 3)
  }
}
