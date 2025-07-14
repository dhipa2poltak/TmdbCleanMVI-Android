package com.dpfht.tmdbcleanmvi.domain.model

data class TrailerModel(
  val id: Int = -1,
  val results: List<Trailer> = listOf()
)
