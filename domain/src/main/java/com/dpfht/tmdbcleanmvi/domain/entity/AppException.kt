package com.dpfht.tmdbcleanmvi.domain.entity

class AppException(
    override val message: String = ""
): Exception(message)
