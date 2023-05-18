package com.dpfht.tmdbcleanmvi.core.domain.usecase

import com.dpfht.tmdbcleanmvi.core.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.Result

interface GetMovieDetailsUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): Result<MovieDetailsDomain>
}
