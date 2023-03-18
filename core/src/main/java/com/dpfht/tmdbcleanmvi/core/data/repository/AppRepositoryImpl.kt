package com.dpfht.tmdbcleanmvi.core.data.repository

import com.dpfht.tmdbcleanmvi.core.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieByGenreResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieDetailsResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieGenreResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieReviewResult
import com.dpfht.tmdbcleanmvi.core.domain.model.GetMovieTrailerResult
import com.dpfht.tmdbcleanmvi.core.domain.usecase.UseCaseResultWrapper
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
  private val remoteDataSource: AppDataSource
): AppRepository {

  override suspend fun getMovieGenre(): UseCaseResultWrapper<GetMovieGenreResult> {
    return remoteDataSource.getMovieGenre()
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): UseCaseResultWrapper<GetMovieByGenreResult> {
    return remoteDataSource.getMoviesByGenre(genreId, page)
  }

  override suspend fun getMovieDetail(movieId: Int): UseCaseResultWrapper<GetMovieDetailsResult> {
    return remoteDataSource.getMovieDetail(movieId)
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): UseCaseResultWrapper<GetMovieReviewResult> {
    return remoteDataSource.getMovieReviews(movieId, page)
  }

  override suspend fun getMovieTrailer(movieId: Int): UseCaseResultWrapper<GetMovieTrailerResult> {
    return remoteDataSource.getMovieTrailer(movieId)
  }
}
