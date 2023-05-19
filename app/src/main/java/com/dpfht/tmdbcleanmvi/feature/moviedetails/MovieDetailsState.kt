package com.dpfht.tmdbcleanmvi.feature.moviedetails

sealed class MovieDetailsState {

  data class IsLoading(val value: Boolean = false): MovieDetailsState()
  data class ViewDetails(val title: String, val overview: String, val imageUrl: String): MovieDetailsState()
  object Idle: MovieDetailsState()
}
