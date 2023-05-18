package com.dpfht.tmdbcleanmvi.core.data.datasource

import com.dpfht.tmdbcleanmvi.core.domain.entity.Result
import com.dpfht.tmdbcleanmvi.core.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvi.core.domain.entity.TrailerDomain

interface AppDataSource {

  suspend fun getMovieGenre(): Result<GenreDomain>

  suspend fun getMoviesByGenre(genreId: String, page: Int): Result<DiscoverMovieByGenreDomain>

  suspend fun getMovieDetail(movieId: Int): Result<MovieDetailsDomain>

  suspend fun getMovieReviews(movieId: Int, page: Int): Result<ReviewDomain>

  suspend fun getMovieTrailer(movieId: Int): Result<TrailerDomain>
}
