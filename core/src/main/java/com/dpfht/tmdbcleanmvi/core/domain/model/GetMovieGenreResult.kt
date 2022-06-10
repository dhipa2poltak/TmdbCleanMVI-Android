package com.dpfht.tmdbcleanmvi.core.domain.model

import com.dpfht.tmdbcleanmvi.core.data.model.remote.Genre

data class GetMovieGenreResult(
  val genres: List<Genre>
)
