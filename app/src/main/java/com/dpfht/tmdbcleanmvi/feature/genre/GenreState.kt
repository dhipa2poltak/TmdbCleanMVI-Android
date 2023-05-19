package com.dpfht.tmdbcleanmvi.feature.genre

sealed class GenreState {

  data class NotifyItemInserted(val value: Int = 0): GenreState()
  data class IsLoading(val value: Boolean = false): GenreState()
  object Idle: GenreState()
}
