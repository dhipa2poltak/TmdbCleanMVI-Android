package com.dpfht.tmdbcleanmvi.data.datasource

import com.dpfht.tmdbcleanmvi.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.tmdbcleanmvi.domain.entity.GenreDomain
import com.dpfht.tmdbcleanmvi.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvi.domain.entity.ReviewDomain
import com.dpfht.tmdbcleanmvi.domain.entity.TrailerDomain

interface AppDataSource {

  suspend fun getMovieGenre(): GenreDomain

  suspend fun getMoviesByGenre(genreId: String, page: Int): DiscoverMovieByGenreDomain

  suspend fun getMovieDetail(movieId: Int): MovieDetailsDomain

  suspend fun getMovieReviews(movieId: Int, page: Int): ReviewDomain

  suspend fun getMovieTrailer(movieId: Int): TrailerDomain
}
