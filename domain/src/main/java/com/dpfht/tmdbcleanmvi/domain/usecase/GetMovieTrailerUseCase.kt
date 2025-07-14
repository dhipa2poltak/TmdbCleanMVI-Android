package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.Result
import com.dpfht.tmdbcleanmvi.domain.model.TrailerModel

interface GetMovieTrailerUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): Result<TrailerModel>
}
