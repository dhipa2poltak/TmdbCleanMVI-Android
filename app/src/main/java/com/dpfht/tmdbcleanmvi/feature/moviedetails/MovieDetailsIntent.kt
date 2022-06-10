package com.dpfht.tmdbcleanmvi.feature.moviedetails

sealed class MovieDetailsIntent {

  object FetchDetails: MovieDetailsIntent()
  object NavigateToReviewScreen: MovieDetailsIntent()
  object NavigateToTrailerScreen: MovieDetailsIntent()
  object EnterIdleState: MovieDetailsIntent()
}