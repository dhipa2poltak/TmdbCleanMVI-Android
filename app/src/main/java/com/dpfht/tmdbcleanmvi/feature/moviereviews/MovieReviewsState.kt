package com.dpfht.tmdbcleanmvi.feature.moviereviews

sealed class MovieReviewsState {

  data class NotifyItemInserted(val value: Int = 0): MovieReviewsState()
  data class IsLoading(val value: Boolean = false): MovieReviewsState()
  object Idle: MovieReviewsState()
}
