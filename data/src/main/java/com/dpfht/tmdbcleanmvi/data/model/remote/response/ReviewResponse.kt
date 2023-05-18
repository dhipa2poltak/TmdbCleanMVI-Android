package com.dpfht.tmdbcleanmvi.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.tmdbcleanmvi.data.model.remote.Review
import com.dpfht.tmdbcleanmvi.domain.entity.AuthorDetailsEntity
import com.dpfht.tmdbcleanmvi.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvi.domain.entity.ReviewEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class ReviewResponse(
    val id: Int? = -1,
    val page: Int? = -1,
    val results: List<Review>? = listOf(),

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = -1,

    @SerializedName("total_results")
    @Expose
    val totalResults: Int? = -1
)

fun ReviewResponse.toDomain(): ReviewDomain {
    val reviewEntities = results?.map {
        var imageUrl = it.authorDetails?.avatarPath ?: ""
        if (imageUrl.startsWith("/")) {
            imageUrl = imageUrl.replaceFirst("/", "")
        }

        if (!imageUrl.startsWith("http")) {
            imageUrl = ""
        }

        val authorDetailsEntity = AuthorDetailsEntity(imageUrl)
        val reviewEntity = ReviewEntity(it.author ?: "", authorDetailsEntity, it.content ?: "")

        reviewEntity
    }

    return ReviewDomain(reviewEntities?.toList() ?: listOf(), this.page ?: -1)
}
