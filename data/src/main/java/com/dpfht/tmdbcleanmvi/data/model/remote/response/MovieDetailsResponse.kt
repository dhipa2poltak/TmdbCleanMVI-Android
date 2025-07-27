package com.dpfht.tmdbcleanmvi.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvi.data.Constants
import com.dpfht.tmdbcleanmvi.data.model.remote.GenreDTO
import com.dpfht.tmdbcleanmvi.data.model.remote.ProductionCompanyDTO
import com.dpfht.tmdbcleanmvi.data.model.remote.ProductionCountryDTO
import com.dpfht.tmdbcleanmvi.data.model.remote.SpokenLanguageDTO
import com.dpfht.tmdbcleanmvi.data.model.remote.toDomain
import com.dpfht.tmdbcleanmvi.domain.model.MovieDetailsModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class MovieDetailsResponse(

    val adult: Boolean? = false,

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = "",

    @SerializedName("belongs_to_collection")
    @Expose
    val belongsToCollection: Any? = null,

    val budget: Int? = -1,
    val genres: List<GenreDTO>? = listOf(),
    val homepage: String? = "",
    val id: Int? = -1,

    @SerializedName("imdb_id")
    @Expose
    val imdbId: String? = "",

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = "",

    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = "",

    val overview: String? = "",
    val popularity: Float? = 0.0f,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = "",

    @SerializedName("production_companies")
    @Expose
    val productionCompanies: List<ProductionCompanyDTO>? = listOf(),

    @SerializedName("production_countries")
    @Expose
    val productionCountries: List<ProductionCountryDTO>? = listOf(),

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = "",

    val revenue: Int? = -1,
    val runtime: Int? = -1,

    @SerializedName("spoken_languages")
    @Expose
    val spokenLanguages: List<SpokenLanguageDTO>? = listOf(),

    val status: String? = "",
    val tagline: String? = "",
    val title: String? = "",
    val video: Boolean? = false,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Float? = 0.0f,

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int? = 0
)

fun MovieDetailsResponse.toDomain(): MovieDetailsModel {
    return MovieDetailsModel(
        id ?: -1,
        title ?: "",
        overview ?: "",
        if (posterPath?.isNotEmpty() == true) Constants.IMAGE_URL_BASE_PATH + posterPath else "",
        genres?.map { it.toDomain() } ?: listOf()
    )
}
