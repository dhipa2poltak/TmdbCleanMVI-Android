package com.dpfht.tmdbcleanmvi.core.domain.model

import com.dpfht.tmdbcleanmvi.core.data.model.remote.Review

data class GetMovieReviewResult(
  val reviews: List<Review>,
  val page: Int
)
