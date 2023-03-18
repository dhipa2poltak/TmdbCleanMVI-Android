package com.dpfht.tmdbcleanmvi.core.domain.usecase

import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieGenreResult

interface GetMovieGenreUseCase {

  suspend operator fun invoke(): UseCaseResultWrapper<GetMovieGenreResult>
}
