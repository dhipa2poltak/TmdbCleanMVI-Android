package com.dpfht.tmdbcleanmvi.feature.moviereviews

sealed class MovieReviewsIntent {

  object FetchReview: MovieReviewsIntent()
  object FetchNextReview: MovieReviewsIntent()
  object EnterIdleState: MovieReviewsIntent()
}