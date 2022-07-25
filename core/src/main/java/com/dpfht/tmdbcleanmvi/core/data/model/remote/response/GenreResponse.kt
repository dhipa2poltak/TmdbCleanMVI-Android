package com.dpfht.tmdbcleanmvi.core.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvi.core.data.model.remote.Genre
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieGenreResult

@Keep
data class GenreResponse(
    val genres: List<Genre>? = null
)

fun GenreResponse.toDomain() = GetMovieGenreResult(genres ?: arrayListOf())
