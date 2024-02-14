package com.dpfht.tmdbcleanmvi.data.model.remote.response

import com.dpfht.tmdbcleanmvi.data.helpers.FileReaderHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TrailerResponseTest {

  @Test
  fun `ensure converting TrailerResponse to domain is success`() {
    val str = FileReaderHelper.readFileAsString("TrailerResponse.json")
    assertTrue(str.isNotEmpty())

    val typeToken = object : TypeToken<TrailerResponse>() {}.type
    val response = Gson().fromJson<TrailerResponse>(str, typeToken)
    val entity = response.toDomain()

    assertTrue(entity.results.size == 13)
  }
}
