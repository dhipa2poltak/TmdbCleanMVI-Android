package com.dpfht.tmdbcleanmvi.core.domain.usecase

import com.dpfht.tmdbcleanmvi.core.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.Result
import com.dpfht.tmdbcleanmvi.core.domain.repository.AppRepository
import javax.inject.Inject

class GetMovieByGenreUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieByGenreUseCase {

  override suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): Result<DiscoverMovieByGenreDomain> {
    return appRepository.getMoviesByGenre(genreId.toString(), page)
  }
}
