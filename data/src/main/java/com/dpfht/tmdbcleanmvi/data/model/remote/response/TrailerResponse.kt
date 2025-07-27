package com.dpfht.tmdbcleanmvi.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvi.data.model.remote.TrailerDTO
import com.dpfht.tmdbcleanmvi.domain.model.TrailerModel
import com.dpfht.tmdbcleanmvi.domain.model.Trailer

@Keep
data class TrailerResponse(
    val id: Int? = -1,
    val results: List<TrailerDTO>? = listOf()
)

fun TrailerResponse.toDomain(): TrailerModel {
    val trailerEntities = results?.map { Trailer(it.id ?: "", it.key ?: "", it.name ?: "", it.site ?: "") }

    return TrailerModel(this.id ?: -1, trailerEntities?.toList() ?: listOf())
}
