package com.dpfht.tmdbcleanmvi.domain.usecase

import com.dpfht.tmdbcleanmvi.domain.model.Result
import com.dpfht.tmdbcleanmvi.domain.model.GenreModel

interface GetMovieGenreUseCase {

  suspend operator fun invoke(): Result<GenreModel>
}
