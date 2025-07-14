package com.dpfht.tmdbcleanmvi.domain.model

class AppException(
    override val message: String = ""
): Exception(message)
