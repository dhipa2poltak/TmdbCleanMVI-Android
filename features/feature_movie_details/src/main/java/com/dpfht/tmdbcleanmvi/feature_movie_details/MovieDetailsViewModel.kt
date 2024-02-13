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

  private var _movieId = -1
  private var title = ""
  private var overview = ""
  private var imageUrl = ""

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
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
            navigateToMovieReviews(_movieId, title)
          }
          NavigateToTrailerScreen -> {
            navigateToMovieTrailer(_movieId)
          }
        }
      }
    }
  }

  override fun start() {
    if (title.isEmpty()) {
      getMovieDetails()
    }
  }

  private fun getMovieDetails() {
    viewModelScope.launch {
      withContext(Dispatchers.Main) { updateState { it.copy(isLoading = true) } }

      when (val result = getMovieDetailsUseCase(_movieId)) {
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
      imageUrl = ""
      if (result.imageUrl.isNotEmpty()) {
        imageUrl = result.imageUrl
      }

      _movieId = result.id
      title = result.title
      overview = result.overview

      updateState { it.copy(title = title, overview = overview, imageUrl =  imageUrl) }
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
