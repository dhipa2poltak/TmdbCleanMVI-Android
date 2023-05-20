package com.dpfht.tmdbcleanmvi.feature_movie_trailer

sealed class MovieTrailerState {

  data class ViewTrailer(val keyVideo: String): MovieTrailerState()
  data class ErrorMessage(val message: String = ""): MovieTrailerState()
  object Idle: MovieTrailerState()
}
