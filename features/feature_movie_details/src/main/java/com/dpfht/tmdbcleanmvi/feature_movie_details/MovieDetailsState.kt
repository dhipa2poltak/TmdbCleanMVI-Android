package com.dpfht.tmdbcleanmvi.feature_movie_details

sealed class MovieDetailsState {

  data class IsLoading(val value: Boolean = false): MovieDetailsState()
  data class ViewDetails(val title: String, val overview: String, val imageUrl: String): MovieDetailsState()
  object Idle: MovieDetailsState()
}
