package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvi.domain.entity.Result
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
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
