package com.dpfht.tmdbcleanmvi.core.usecase

import com.dpfht.tmdbcleanmvi.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieDetailsResult
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieDetailsResult> {
    return appRepository.getMovieDetail(movieId)
  }
}
