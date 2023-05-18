package com.dpfht.tmdbcleanmvi.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvi.data.model.remote.Genre
import com.dpfht.tmdbcleanmvi.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvi.domain.entity.GenreEntity

@Keep
data class GenreResponse(
    val genres: List<Genre>? = listOf()
)

fun GenreResponse.toDomain(): GenreDomain {
    val genreEntities = genres?.map { GenreEntity(it.id ?: -1, it.name ?: "") }

    return GenreDomain(genreEntities?.toList() ?: listOf())
}
