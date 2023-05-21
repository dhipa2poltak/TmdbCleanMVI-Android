package com.dpfht.tmdbcleanmvi.feature_movies_by_genre

data class MoviesByGenreState(
  val itemInserted: Int = 0,
  val isLoading: Boolean = false
)
