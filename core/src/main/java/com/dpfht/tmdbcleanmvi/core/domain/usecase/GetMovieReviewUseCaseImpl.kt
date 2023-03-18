package com.dpfht.tmdbcleanmvi.core.domain.usecase

import com.dpfht.tmdbcleanmvi.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieReviewResult
import javax.inject.Inject

class GetMovieReviewUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieReviewUseCase {

  override suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): UseCaseResultWrapper<GetMovieReviewResult> {
    return appRepository.getMovieReviews(movieId, page)
  }
}
