package com.dpfht.tmdbcleanmvi.feature_movie_reviews

data class MovieReviewsState(
  val itemInserted: Int = 0,
  val isLoading: Boolean = false
)
