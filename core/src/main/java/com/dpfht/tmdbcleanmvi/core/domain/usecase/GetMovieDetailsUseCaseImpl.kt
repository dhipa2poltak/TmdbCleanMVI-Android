package com.dpfht.tmdbcleanmvi.core.domain.usecase

import com.dpfht.tmdbcleanmvi.core.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.Result
import com.dpfht.tmdbcleanmvi.core.domain.repository.AppRepository
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): Result<MovieDetailsDomain> {
    return appRepository.getMovieDetail(movieId)
  }
}
