package com.dpfht.tmdbcleanmvi.framework.rest.api

import com.dpfht.tmdbcleanmvi.core.data.model.remote.response.toDomain
import com.dpfht.tmdbcleanmvi.core.data.repository.AppDataSource
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieGenreResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieReviewResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbcleanmvi.core.usecase.UseCaseResultWrapper
import com.dpfht.tmdbcleanmvi.core.usecase.UseCaseResultWrapper.ErrorResult
import com.dpfht.tmdbcleanmvi.framework.rest.api.ResultWrapper.GenericError
import com.dpfht.tmdbcleanmvi.framework.rest.api.ResultWrapper.NetworkError
import com.dpfht.tmdbcleanmvi.framework.rest.api.ResultWrapper.Success
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
  private val restService: RestService
): AppDataSource {

  override suspend fun getMovieGenre(): UseCaseResultWrapper<GetMovieGenreResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieGenre() }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): UseCaseResultWrapper<GetMovieByGenreResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMoviesByGenre(genreId, page) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieDetail(movieId: Int): UseCaseResultWrapper<GetMovieDetailsResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieDetail(movieId) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): UseCaseResultWrapper<GetMovieReviewResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieReviews(movieId, page) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }

  override suspend fun getMovieTrailer(movieId: Int): UseCaseResultWrapper<GetMovieTrailerResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getMovieTrailers(movieId) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          ErrorResult(result.error.statusMessage ?: "")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }
}
