package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.AppException
import com.dpfht.tmdbcleanmvi.domain.model.DiscoverMovieByGenreModel
import com.dpfht.tmdbcleanmvi.domain.model.Result
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
import javax.inject.Inject

class GetMovieByGenreUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieByGenreUseCase {

  override suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): Result<DiscoverMovieByGenreModel> {
    return try {
      Result.Success(appRepository.getMoviesByGenre(genreId.toString(), page))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
