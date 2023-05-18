package com.dpfht.tmdbcleanmvi.core.domain.usecase

import com.dpfht.tmdbcleanmvi.core.domain.entity.Result
import com.dpfht.tmdbcleanmvi.core.domain.entity.TrailerDomain

interface GetMovieTrailerUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): Result<TrailerDomain>
}
