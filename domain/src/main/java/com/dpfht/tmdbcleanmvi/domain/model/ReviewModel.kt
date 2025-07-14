package com.dpfht.tmdbcleanmvi.domain.model

data class ReviewModel(
  val results: List<Review> = listOf(),
  val page: Int = -1,
)
