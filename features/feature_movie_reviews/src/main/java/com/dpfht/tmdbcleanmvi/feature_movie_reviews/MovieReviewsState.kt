package com.dpfht.tmdbcleanmvi.feature_movie_reviews

sealed class MovieReviewsState {

  data class NotifyItemInserted(val value: Int = 0): MovieReviewsState()
  data class IsLoading(val value: Boolean = false): MovieReviewsState()
  object Idle: MovieReviewsState()
}

