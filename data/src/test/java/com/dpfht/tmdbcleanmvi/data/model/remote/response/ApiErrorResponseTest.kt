package com.dpfht.tmdbcleanmvi.data.model.remote.response

import com.dpfht.tmdbcleanmvi.data.helpers.FileReaderHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiErrorResponseTest {

    @Test
    fun `testing ApiErrorResponse`() {
        val str = FileReaderHelper.readFileAsString("ApiErrorResponse.json")
        assertTrue(str.isNotEmpty())

        val typeToken = object : TypeToken<ApiErrorResponse>() {}.type
        val response = Gson().fromJson<ApiErrorResponse>(str, typeToken)

        assertTrue(response.success == false)
        assertTrue(response.statusCode == 34)
        assertTrue(response.statusMessage == "The resource you requested could not be found.")
    }
}
