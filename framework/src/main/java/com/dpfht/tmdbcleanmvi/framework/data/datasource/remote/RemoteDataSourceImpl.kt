package com.dpfht.tmdbcleanmvi.framework.data.datasource.remote

import android.content.Context
import com.dpfht.tmdbcleanmvi.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvi.data.model.remote.response.ApiErrorResponse
import com.dpfht.tmdbcleanmvi.data.model.remote.response.toDomain
import com.dpfht.tmdbcleanmvi.domain.model.AppException
import com.dpfht.tmdbcleanmvi.domain.model.DiscoverMovieByGenreModel
import com.dpfht.tmdbcleanmvi.domain.model.GenreModel
import com.dpfht.tmdbcleanmvi.domain.model.MovieDetailsModel
import com.dpfht.tmdbcleanmvi.domain.model.ReviewModel
import com.dpfht.tmdbcleanmvi.domain.model.TrailerModel
import com.dpfht.tmdbcleanmvi.framework.R
import com.dpfht.tmdbcleanmvi.framework.data.datasource.remote.rest.RestService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
  private val context: Context,
  private val restService: RestService
): AppDataSource {

  override suspend fun getMovieGenre(): GenreModel {
    return safeApiCall(Dispatchers.IO) { restService.getMovieGenre().toDomain() }
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): DiscoverMovieByGenreModel {
    return safeApiCall(Dispatchers.IO) { restService.getMoviesByGenre(genreId, page).toDomain() }
  }

  override suspend fun getMovieDetail(movieId: Int): MovieDetailsModel {
    return safeApiCall(Dispatchers.IO) { restService.getMovieDetail(movieId).toDomain() }
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): ReviewModel {
    return safeApiCall(Dispatchers.IO) { restService.getMovieReviews(movieId, page).toDomain() }
  }

  override suspend fun getMovieTrailer(movieId: Int): TrailerModel {
    return safeApiCall(Dispatchers.IO) { restService.getMovieTrailers(movieId).toDomain() }
  }

  private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): T {
    return withContext(dispatcher) {
      try {
        apiCall.invoke()
      } catch (t: Throwable) {
        throw when (t) {
          is IOException -> AppException(context.getString(R.string.framework_text_error_connection))
          is HttpException -> {
            val errorResponse = convertErrorBody(t)

            AppException(errorResponse?.statusMessage ?: context.getString(R.string.framework_text_error_http))
          }
          else -> {
            AppException(context.getString(R.string.framework_text_error_conversion))
          }
        }
      }
    }
  }

  private fun convertErrorBody(t: HttpException): ApiErrorResponse? {
    return try {
      t.response()?.errorBody()?.source().let {
        val json = it?.readString(Charset.defaultCharset())
        val typeToken = object : TypeToken<ApiErrorResponse>() {}.type
        val errorResponse = Gson().fromJson<ApiErrorResponse>(json, typeToken)
        errorResponse
      }
    } catch (e: Exception) {
      null
    }
  }
}
