package com.dpfht.tmdbcleanmvi.data.repository

import com.dpfht.tmdbcleanmvi.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvi.domain.model.DiscoverMovieByGenreModel
import com.dpfht.tmdbcleanmvi.domain.model.GenreModel
import com.dpfht.tmdbcleanmvi.domain.model.MovieDetailsModel
import com.dpfht.tmdbcleanmvi.domain.model.ReviewModel
import com.dpfht.tmdbcleanmvi.domain.model.TrailerModel
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
  private val remoteDataSource: AppDataSource
): AppRepository {

  override suspend fun getMovieGenre(): GenreModel {
    return remoteDataSource.getMovieGenre()
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): DiscoverMovieByGenreModel {
    return remoteDataSource.getMoviesByGenre(genreId, page)
  }

  override suspend fun getMovieDetail(movieId: Int): MovieDetailsModel {
    return remoteDataSource.getMovieDetail(movieId)
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): ReviewModel {
    return remoteDataSource.getMovieReviews(movieId, page)
  }

  override suspend fun getMovieTrailer(movieId: Int): TrailerModel {
    return remoteDataSource.getMovieTrailer(movieId)
  }
}
