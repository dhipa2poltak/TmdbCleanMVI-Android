package com.dpfht.tmdbcleanmvi.core.usecase

import com.dpfht.tmdbcleanmvi.core.data.repository.AppRepository
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieTrailerResult
import javax.inject.Inject

class GetMovieTrailerUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieTrailerUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieTrailerResult> {
    return appRepository.getMovieTrailer(movieId)
  }
}
