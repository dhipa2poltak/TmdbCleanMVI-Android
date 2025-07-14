package com.dpfht.tmdbcleanmvi.feature_movie_reviews

data class MovieReviewsState(
  val movieId: Int = -1,
  val page: Int = 0,
  val isEmptyNextResponse: Boolean = false,
  val isLoading: Boolean = false,
  val isNoReviews: Boolean = false
)
