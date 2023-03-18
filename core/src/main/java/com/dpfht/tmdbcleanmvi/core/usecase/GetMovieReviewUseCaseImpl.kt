package com.dpfht.tmdbcleanmvi.core.usecase

import com.dpfht.tmdbcleanmvi.core.data.repository.AppRepository
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
