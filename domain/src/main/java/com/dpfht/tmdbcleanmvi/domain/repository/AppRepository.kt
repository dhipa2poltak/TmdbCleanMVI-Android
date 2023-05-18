package com.dpfht.tmdbcleanmvi.domain.repository

import com.dpfht.tmdbcleanmvi.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvi.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvi.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvi.domain.entity.Result
import com.dpfht.tmdbcleanmvi.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvi.domain.entity.TrailerDomain

interface AppRepository {

  suspend fun getMovieGenre(): Result<GenreDomain>

  suspend fun getMoviesByGenre(genreId: String, page: Int): Result<DiscoverMovieByGenreDomain>

  suspend fun getMovieDetail(movieId: Int): Result<MovieDetailsDomain>

  suspend fun getMovieReviews(movieId: Int, page: Int): Result<ReviewDomain>

  suspend fun getMovieTrailer(movieId: Int): Result<TrailerDomain>
}
