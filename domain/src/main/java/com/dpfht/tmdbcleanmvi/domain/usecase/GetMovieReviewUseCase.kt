package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.Result
import com.dpfht.tmdbcleanmvi.domain.model.ReviewModel

interface GetMovieReviewUseCase {

  suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): Result<ReviewModel>
}
