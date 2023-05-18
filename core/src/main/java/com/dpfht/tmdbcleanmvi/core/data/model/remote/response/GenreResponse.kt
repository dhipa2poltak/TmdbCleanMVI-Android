package com.dpfht.tmdbcleanmvi.core.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvi.core.data.model.remote.Genre
import com.dpfht.tmdbcleanmvi.core.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.GenreEntity

@Keep
data class GenreResponse(
    val genres: List<Genre>? = listOf()
)

fun GenreResponse.toDomain(): GenreDomain {
    val genreEntities = genres?.map { GenreEntity(it.id ?: -1, it.name ?: "") }

    return GenreDomain(genreEntities?.toList() ?: listOf())
}
