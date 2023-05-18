package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvi.domain.entity.Result
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
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
