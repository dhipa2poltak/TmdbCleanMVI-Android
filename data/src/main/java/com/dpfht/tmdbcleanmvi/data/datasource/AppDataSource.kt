package com.dpfht.tmdbcleanmvi.data.datasource

import com.dpfht.tmdbcleanmvi.domain.model.DiscoverMovieByGenreModel
import com.dpfht.tmdbcleanmvi.domain.model.GenreModel
import com.dpfht.tmdbcleanmvi.domain.model.MovieDetailsModel
import com.dpfht.tmdbcleanmvi.domain.model.ReviewModel
import com.dpfht.tmdbcleanmvi.domain.model.TrailerModel

interface AppDataSource {

  suspend fun getMovieGenre(): GenreModel

  suspend fun getMoviesByGenre(genreId: String, page: Int): DiscoverMovieByGenreModel

  suspend fun getMovieDetail(movieId: Int): MovieDetailsModel

  suspend fun getMovieReviews(movieId: Int, page: Int): ReviewModel

  suspend fun getMovieTrailer(movieId: Int): TrailerModel
}
