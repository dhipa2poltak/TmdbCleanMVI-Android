package com.dpfht.tmdbcleanmvi.core.domain.usecase

import com.dpfht.tmdbcleanmvi.core.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.Result
import com.dpfht.tmdbcleanmvi.core.domain.repository.AppRepository
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
