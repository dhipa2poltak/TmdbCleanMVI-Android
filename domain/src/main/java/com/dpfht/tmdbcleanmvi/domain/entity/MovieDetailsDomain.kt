package com.dpfht.tmdbcleanmvi.domain.entity

data class MovieDetailsDomain(
  val id: Int = -1,
  val title: String = "",
  val overview: String = "",
  val imageUrl: String = ""
)
