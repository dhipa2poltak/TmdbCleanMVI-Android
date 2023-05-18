package com.dpfht.tmdbcleanmvi.domain.entity

data class TrailerDomain(
  val id: Int = -1,
  val results: List<TrailerEntity> = listOf()
)
