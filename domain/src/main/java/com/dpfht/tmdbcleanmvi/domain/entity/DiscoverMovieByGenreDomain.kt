package com.dpfht.tmdbcleanmvi.domain.entity

data class DiscoverMovieByGenreDomain(
  val page: Int = 0,
  val results: List<MovieEntity> = listOf(),
  val totalPages: Int = 0,
  val totalResults: Int = 0
)
