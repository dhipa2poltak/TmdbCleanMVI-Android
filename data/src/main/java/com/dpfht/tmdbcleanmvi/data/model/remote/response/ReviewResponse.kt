package com.dpfht.tmdbcleanmvi.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvi.data.model.remote.ReviewDTO
import com.dpfht.tmdbcleanmvi.domain.model.AuthorDetails
import com.dpfht.tmdbcleanmvi.domain.model.ReviewModel
import com.dpfht.tmdbcleanmvi.domain.model.Review
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class ReviewResponse(
    val id: Int? = -1,
    val page: Int? = -1,
    val results: List<ReviewDTO>? = listOf(),

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = -1,

    @SerializedName("total_results")
    @Expose
    val totalResults: Int? = -1
)

fun ReviewResponse.toDomain(): ReviewModel {
    val reviewEntities = results?.map {
        var imageUrl = it.authorDetails?.avatarPath ?: ""
        if (imageUrl.startsWith("/")) {
            imageUrl = imageUrl.replaceFirst("/", "")
        }

        if (!imageUrl.startsWith("http")) {
            imageUrl = ""
        }

        val authorDetailsEntity = AuthorDetails(imageUrl)
        val reviewEntity = Review(it.author ?: "", authorDetailsEntity, it.content ?: "")

        reviewEntity
    }

    return ReviewModel(reviewEntities?.toList() ?: listOf(), this.page ?: -1)
}
