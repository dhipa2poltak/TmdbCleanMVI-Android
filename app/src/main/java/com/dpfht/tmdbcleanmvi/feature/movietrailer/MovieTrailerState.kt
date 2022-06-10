package com.dpfht.tmdbcleanmvi.feature.movietrailer

sealed class MovieTrailerState {

  data class ViewTrailer(val keyVideo: String): MovieTrailerState()
  data class ErrorMessage(val message: String = ""): MovieTrailerState()
  object Idle: MovieTrailerState()
}
