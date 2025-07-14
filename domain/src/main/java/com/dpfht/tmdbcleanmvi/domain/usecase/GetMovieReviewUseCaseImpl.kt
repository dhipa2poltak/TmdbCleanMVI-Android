package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.AppException
import com.dpfht.tmdbcleanmvi.domain.model.ReviewModel
import com.dpfht.tmdbcleanmvi.domain.model.Result
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
import javax.inject.Inject

class GetMovieReviewUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieReviewUseCase {

  override suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): Result<ReviewModel> {
    return try {
      Result.Success(appRepository.getMovieReviews(movieId, page))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
