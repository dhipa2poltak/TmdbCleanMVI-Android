package com.dpfht.tmdbcleanmvi.feature_movies_by_genre

sealed class MoviesByGenreState {

  data class NotifyItemInserted(val value: Int = 0): MoviesByGenreState()
  data class IsLoading(val value: Boolean = false): MoviesByGenreState()
  object Idle: MoviesByGenreState()
}
