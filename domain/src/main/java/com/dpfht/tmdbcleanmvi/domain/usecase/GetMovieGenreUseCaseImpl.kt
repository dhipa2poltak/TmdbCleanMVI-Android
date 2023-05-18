package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvi.domain.entity.Result
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
import javax.inject.Inject

class GetMovieGenreUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieGenreUseCase {

  override suspend operator fun invoke(): Result<GenreDomain> {
    return appRepository.getMovieGenre()
  }
}
