package com.dpfht.tmdbcleanmvi.core.domain.usecase

import com.dpfht.tmdbcleanmvi.core.domain.entity.Result
import com.dpfht.tmdbcleanmvi.core.domain.entity.ReviewDomain

interface GetMovieReviewUseCase {

  suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): Result<ReviewDomain>
}
