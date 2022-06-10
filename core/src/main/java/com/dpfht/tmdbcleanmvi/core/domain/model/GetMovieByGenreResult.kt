package com.dpfht.tmdbcleanmvi.core.domain.model

import com.dpfht.tmdbcleanmvi.core.data.model.remote.Movie

data class GetMovieByGenreResult(
  val movies: List<Movie>,
  val page: Int
)
