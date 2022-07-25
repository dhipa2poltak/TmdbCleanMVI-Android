package com.dpfht.tmdbcleanmvi.core.data.model.remote

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

@Keep
@Suppress("unused")
data class Review(
    val author: String? = null,

    @SerializedName("author_details")
    @Expose
    val authorDetails: AuthorDetails? = null,

    val content: String? = null,

    @SerializedName("created_at")
    @Expose
    val createdAt: Date? = null,

    val id: String? = null,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: Date? = null,

    val url: String? = null
)
