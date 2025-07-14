package com.dpfht.tmdbcleanmvi.domain.model

data class DiscoverMovieByGenreModel(
  val page: Int = 0,
  val results: List<Movie> = listOf(),
  val totalPages: Int = 0,
  val totalResults: Int = 0
)
