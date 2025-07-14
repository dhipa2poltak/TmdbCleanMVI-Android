package com.dpfht.tmdbcleanmvi.domain.model

data class Movie(
  val id: Int = -1,
  val title: String = "",
  val overview: String = "",
  val imageUrl: String = ""
)
