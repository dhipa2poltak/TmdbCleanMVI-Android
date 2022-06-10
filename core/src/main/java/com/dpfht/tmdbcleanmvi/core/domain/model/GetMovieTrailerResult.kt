package com.dpfht.tmdbcleanmvi.core.domain.model

import com.dpfht.tmdbcleanmvi.core.data.model.remote.Trailer

data class GetMovieTrailerResult(
  val trailers: List<Trailer>
)
