package com.dpfht.tmdbcleanmvi.core.data.repository

import com.dpfht.tmdbcleanmvi.core.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvi.core.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.Result
import com.dpfht.tmdbcleanmvi.core.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.TrailerDomain
import com.dpfht.tmdbcleanmvi.core.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val remoteDataSource: AppDataSource): AppRepository {

  override suspend fun getMovieGenre(): Result<GenreDomain> {
    return remoteDataSource.getMovieGenre()
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): Result<DiscoverMovieByGenreDomain> {
    return remoteDataSource.getMoviesByGenre(genreId, page)
  }

  override suspend fun getMovieDetail(movieId: Int): Result<MovieDetailsDomain> {
    return remoteDataSource.getMovieDetail(movieId)
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): Result<ReviewDomain> {
    return remoteDataSource.getMovieReviews(movieId, page)
  }

  override suspend fun getMovieTrailer(movieId: Int): Result<TrailerDomain> {
    return remoteDataSource.getMovieTrailer(movieId)
  }
}
