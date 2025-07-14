package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.Result
import com.dpfht.tmdbcleanmvi.domain.model.MovieDetailsModel

interface GetMovieDetailsUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): Result<MovieDetailsModel>
}
