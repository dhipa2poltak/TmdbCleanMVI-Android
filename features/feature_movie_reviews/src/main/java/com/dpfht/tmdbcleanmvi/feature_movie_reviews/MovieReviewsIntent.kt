package com.dpfht.tmdbcleanmvi.feature_movie_reviews

sealed class MovieReviewsIntent {

  object FetchReview: MovieReviewsIntent()
  object FetchNextReview: MovieReviewsIntent()
}
