package com.dpfht.tmdbcleanmvi.core.domain.entity

data class TrailerDomain(
  val id: Int = -1,
  val results: List<TrailerEntity> = listOf()
)
