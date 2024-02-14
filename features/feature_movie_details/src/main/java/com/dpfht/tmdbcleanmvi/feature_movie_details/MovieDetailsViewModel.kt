package com.dpfht.tmdbcleanmvi.feature_movie_details

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Error
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbcleanmvi.feature_movie_details.MovieDetailsIntent.FetchDetails
import com.dpfht.tmdbcleanmvi.feature_movie_details.MovieDetailsIntent.NavigateToReviewScreen
import com.dpfht.tmdbcleanmvi.feature_movie_details.MovieDetailsIntent.NavigateToTrailerScreen
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
  private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
  private val navigationService: NavigationService
): BaseViewModel<MovieDetailsIntent, MovieDetailsState>() {

  override val _state = MutableStateFlow(MovieDetailsState())

  fun setMovieId(movieId: Int) {
    viewModelScope.launch {
      updateState { it.copy(movieId = movieId) }
    }
  }

  init {
    handlerIntent()
  }

  private fun handlerIntent() {
    viewModelScope.launch {
      intents.consumeAsFlow().collect { intent ->
        when (intent) {
          FetchDetails -> {
            start()
          }
          NavigateToReviewScreen -> {
            navigateToMovieReviews(state.value.movieId, state.value.title)
          }
          NavigateToTrailerScreen -> {
            navigateToMovieTrailer(state.value.movieId)
          }
        }
      }
    }
  }

  override fun start() {
    if (state.value.title.isEmpty()) {
      getMovieDetails()
    }
  }

  private fun getMovieDetails() {
    viewModelScope.launch {
      withContext(Dispatchers.Main) { updateState { it.copy(isLoading = true) } }

      when (val result = getMovieDetailsUseCase(state.value.movieId)) {
        is Success -> {
          onSuccess(result.value)
        }
        is Error -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(result: MovieDetailsDomain) {
    viewModelScope.launch(Dispatchers.Main) {
      val imageUrl = result.imageUrl
      val movieId = result.id
      val title = result.title
      val overview = result.overview

      var strGenres = ""
      for (genre in result.genres) {
        if (strGenres.isEmpty()) {
          strGenres = genre.name
        } else {
          strGenres += ", ${genre.name}"
        }
      }

      updateState { it.copy(movieId = movieId, title = title, overview = overview, imageUrl =  imageUrl, genres = strGenres) }
      updateState { it.copy(isLoading = false) }
    }
  }

  private fun onError(message: String) {
    viewModelScope.launch(Dispatchers.Main) {
      updateState { it.copy(isLoading = false) }

      if (message.isNotEmpty()) {
        navigationService.navigateToErrorMessage(message)
      }
    }
  }

  private fun navigateToMovieReviews(movieId: Int, title: String) {
    navigationService.navigateToMovieReviews(movieId, title)
  }

  private fun navigateToMovieTrailer(movieId: Int) {
    navigationService.navigateToMovieTrailer(movieId)
  }
}
