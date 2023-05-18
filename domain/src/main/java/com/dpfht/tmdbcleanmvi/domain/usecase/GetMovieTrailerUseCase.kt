package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.entity.Result
import com.dpfht.tmdbcleanmvi.domain.entity.TrailerDomain

interface GetMovieTrailerUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): Result<TrailerDomain>
}
