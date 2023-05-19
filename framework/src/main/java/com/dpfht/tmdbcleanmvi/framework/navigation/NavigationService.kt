package com.dpfht.tmdbcleanmvi.framework.navigation

interface NavigationService {
  fun navigateToGender()
  fun navigateToMoviesByGender(genreId: Int, genreName: String)
  fun navigateToMovieDetails(movieId: Int)
  fun navigateToMovieReviews(movieId: Int, movieTitle: String)
  fun navigateToMovieTrailer(movieId: Int)
  fun navigateToErrorMessage(message: String)
}
