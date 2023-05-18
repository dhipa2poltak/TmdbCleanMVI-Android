package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.entity.GenreDomain

interface GetMovieGenreUseCase {

  suspend operator fun invoke(): Result<GenreDomain>
}
