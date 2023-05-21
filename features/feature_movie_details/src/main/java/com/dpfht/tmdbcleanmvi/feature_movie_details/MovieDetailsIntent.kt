package com.dpfht.tmdbcleanmvi.feature_movie_details

sealed class MovieDetailsIntent {

  object FetchDetails: MovieDetailsIntent()
  object NavigateToReviewScreen: MovieDetailsIntent()
  object NavigateToTrailerScreen: MovieDetailsIntent()
}
