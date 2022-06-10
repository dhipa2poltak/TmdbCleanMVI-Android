package com.dpfht.tmdbcleanmvi.core.usecase

import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieDetailsResult

interface GetMovieDetailsUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): UseCaseResultWrapper<GetMovieDetailsResult>
}
