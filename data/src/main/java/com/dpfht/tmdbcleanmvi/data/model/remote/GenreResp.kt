package com.dpfht.tmdbcleanmvi.data.model.remote

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvi.domain.model.Genre

@Keep
data class GenreResp(
    val id: Int? = -1,
    val name: String? = ""
)

fun GenreResp.toDomain(): Genre {
    return Genre(
        id ?: -1,
        name ?: "",
    )
}
