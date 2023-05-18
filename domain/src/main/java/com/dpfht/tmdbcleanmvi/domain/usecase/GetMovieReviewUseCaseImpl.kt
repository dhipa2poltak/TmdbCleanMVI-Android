package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvi.domain.entity.Result
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
import javax.inject.Inject

class GetMovieReviewUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieReviewUseCase {

  override suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): Result<ReviewDomain> {
    return appRepository.getMovieReviews(movieId, page)
  }
}
