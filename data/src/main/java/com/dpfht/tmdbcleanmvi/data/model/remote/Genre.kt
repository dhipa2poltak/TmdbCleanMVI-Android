package com.dpfht.tmdbcleanmvi.data.model.remote

import androidx.annotation.Keep

@Keep
data class Genre(
    val id: Int? = -1,
    val name: String? = ""
)
