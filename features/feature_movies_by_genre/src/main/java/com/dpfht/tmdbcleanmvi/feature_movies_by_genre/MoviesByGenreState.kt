package com.dpfht.tmdbcleanmvi.feature_movies_by_genre

data class MoviesByGenreState(
  val genreId: Int = -1,
  val page: Int = 0,
  val isEmptyNextResponse: Boolean = false,
  val isLoading: Boolean = false
)
