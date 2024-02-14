package com.dpfht.tmdbcleanmvi.feature_movie_details

data class MovieDetailsState(
  val movieId: Int = -1,
  val isLoading: Boolean = false,
  val title: String = "",
  val overview: String = "",
  val imageUrl: String = "",
  val genres: String = ""
)
