package com.dpfht.tmdbcleanmvi.feature_movies_by_genre

sealed class MoviesByGenreIntent {

  object FetchMovie: MoviesByGenreIntent()
  object FetchNextMovie: MoviesByGenreIntent()
  data class NavigateToNextScreen(val position: Int): MoviesByGenreIntent()
}
