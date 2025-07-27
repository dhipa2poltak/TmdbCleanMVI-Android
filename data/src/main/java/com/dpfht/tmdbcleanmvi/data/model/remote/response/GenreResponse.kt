package com.dpfht.tmdbcleanmvi.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvi.data.model.remote.GenreDTO
import com.dpfht.tmdbcleanmvi.domain.model.GenreModel
import com.dpfht.tmdbcleanmvi.domain.model.Genre

@Keep
data class GenreResponse(
    val genres: List<GenreDTO>? = listOf()
)

fun GenreResponse.toDomain(): GenreModel {
    val genreEntities = genres?.map { Genre(it.id ?: -1, it.name ?: "") }

    return GenreModel(genreEntities?.toList() ?: listOf())
}
