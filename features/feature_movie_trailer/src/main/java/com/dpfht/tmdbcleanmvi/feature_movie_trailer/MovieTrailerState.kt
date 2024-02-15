package com.dpfht.tmdbcleanmvi.feature_movie_trailer

data class MovieTrailerState(
  val movieId: Int = -1,
  val keyVideo: String = "",
  val errorMessage: String = ""
)
