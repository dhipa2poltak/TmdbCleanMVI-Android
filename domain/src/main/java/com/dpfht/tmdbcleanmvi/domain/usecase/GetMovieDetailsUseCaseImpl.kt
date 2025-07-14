package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.AppException
import com.dpfht.tmdbcleanmvi.domain.model.MovieDetailsModel
import com.dpfht.tmdbcleanmvi.domain.model.Result
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): Result<MovieDetailsModel> {
    return try {
      Result.Success(appRepository.getMovieDetail(movieId))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
