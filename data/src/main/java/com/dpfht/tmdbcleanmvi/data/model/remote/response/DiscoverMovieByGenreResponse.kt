package com.dpfht.tmdbcleanmvi.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvi.data.Constants
import com.dpfht.tmdbcleanmvi.data.model.remote.MovieDTO
import com.dpfht.tmdbcleanmvi.domain.model.DiscoverMovieByGenreModel
import com.dpfht.tmdbcleanmvi.domain.model.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class DiscoverMovieByGenreResponse(
    val page: Int? = -1,
    val results: List<MovieDTO>? = listOf(),

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = -1,

    @SerializedName("total_results")
    @Expose
    val totalResults: Int? = -1
)

fun DiscoverMovieByGenreResponse.toDomain(): DiscoverMovieByGenreModel {
  val movieEntities = results?.map {
    Movie(
      it.id ?: -1,
      it.title ?: "",
      it.overview ?: "",
      if (it.posterPath?.isNotEmpty() == true) Constants.IMAGE_URL_BASE_PATH + it.posterPath else ""
    )
  }

  return DiscoverMovieByGenreModel(
    page ?: -1,
    movieEntities?.toList() ?: listOf(),
    totalPages ?: -1,
    totalResults ?: -1
  )
}
