package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.Result
import com.dpfht.tmdbcleanmvi.domain.model.DiscoverMovieByGenreModel

interface GetMovieByGenreUseCase {

  suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): Result<DiscoverMovieByGenreModel>
}
