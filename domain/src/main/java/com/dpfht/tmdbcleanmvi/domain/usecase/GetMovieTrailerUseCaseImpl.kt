package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.AppException
import com.dpfht.tmdbcleanmvi.domain.model.Result
import com.dpfht.tmdbcleanmvi.domain.model.TrailerModel
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
import javax.inject.Inject

class GetMovieTrailerUseCaseImpl @Inject constructor(
  private val appRepository: AppRepository
): GetMovieTrailerUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): Result<TrailerModel> {
    return try {
      Result.Success(appRepository.getMovieTrailer(movieId))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
