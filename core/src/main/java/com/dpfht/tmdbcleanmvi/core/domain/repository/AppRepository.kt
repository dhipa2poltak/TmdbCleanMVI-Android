package com.dpfht.tmdbcleanmvi.core.domain.repository

import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieGenreResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieReviewResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbcleanmvi.core.domain.usecase.UseCaseResultWrapper

interface AppRepository {

  suspend fun getMovieGenre(): UseCaseResultWrapper<GetMovieGenreResult>

  suspend fun getMoviesByGenre(genreId: String, page: Int): UseCaseResultWrapper<GetMovieByGenreResult>

  suspend fun getMovieDetail(movieId: Int): UseCaseResultWrapper<GetMovieDetailsResult>

  suspend fun getMovieReviews(movieId: Int, page: Int): UseCaseResultWrapper<GetMovieReviewResult>

  suspend fun getMovieTrailer(movieId: Int): UseCaseResultWrapper<GetMovieTrailerResult>
}
