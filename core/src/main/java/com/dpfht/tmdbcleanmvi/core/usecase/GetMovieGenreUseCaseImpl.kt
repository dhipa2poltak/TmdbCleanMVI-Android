package com.dpfht.tmdbcleanmvi.core.usecase

import com.dpfht.tmdbcleanmvi.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieGenreResult
import javax.inject.Inject

class GetMovieGenreUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieGenreUseCase {

  override suspend operator fun invoke(): UseCaseResultWrapper<GetMovieGenreResult> {
    return appRepository.getMovieGenre()
  }
}
