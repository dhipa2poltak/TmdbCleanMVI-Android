package com.dpfht.tmdbcleanmvi.core.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvi.core.data.model.remote.Trailer
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieTrailerResult

@Keep
data class TrailerResponse(
    val id: Int = 0,
    val results: ArrayList<Trailer>? = null
)

fun TrailerResponse.toDomain() = GetMovieTrailerResult(this.results ?: arrayListOf())
