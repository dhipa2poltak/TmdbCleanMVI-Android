package com.dpfht.tmdbcleanmvi.domain.entity

data class ReviewEntity(
  val author: String = "",
  val authorDetails: AuthorDetailsEntity? = null,
  val content: String = "",
)
