package com.dpfht.tmdbcleanmvi.feature.moviesbygenre

sealed class MoviesByGenreState {

  data class NotifyItemInserted(val value: Int = 0): MoviesByGenreState()
  data class IsLoading(val value: Boolean = false): MoviesByGenreState()
  object Idle: MoviesByGenreState()
}
