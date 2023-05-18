package com.dpfht.tmdbcleanmvi.core.domain.usecase

import com.dpfht.tmdbcleanmvi.core.domain.entity.Result
import com.dpfht.tmdbcleanmvi.core.domain.entity.TrailerDomain
import com.dpfht.tmdbcleanmvi.core.domain.repository.AppRepository
import javax.inject.Inject

class GetMovieTrailerUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieTrailerUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): Result<TrailerDomain> {
    return appRepository.getMovieTrailer(movieId)
  }
}
