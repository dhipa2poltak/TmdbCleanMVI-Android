package com.dpfht.tmdbcleanmvi.feature_movie_trailer

sealed class MovieTrailerIntent {

  object FetchTrailer: MovieTrailerIntent()
  object EnterIdleState: MovieTrailerIntent()
}
