package com.dpfht.tmdbcleanmvi.feature.movietrailer

sealed class MovieTrailerIntent {

  object FetchTrailer: MovieTrailerIntent()
  object EnterIdleState: MovieTrailerIntent()
}
