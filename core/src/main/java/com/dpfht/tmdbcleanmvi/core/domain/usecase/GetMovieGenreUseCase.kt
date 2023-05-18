package com.dpfht.tmdbcleanmvi.core.domain.usecase

import com.dpfht.tmdbcleanmvi.core.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.Result

interface GetMovieGenreUseCase {

  suspend operator fun invoke(): Result<GenreDomain>
}
