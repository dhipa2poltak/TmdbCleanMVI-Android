package com.dpfht.tmdbcleanmvi.feature_genre

sealed class GenreIntent {

  object FetchGenre: GenreIntent()
  data class NavigateToNextScreen(val position: Int): GenreIntent()
}
