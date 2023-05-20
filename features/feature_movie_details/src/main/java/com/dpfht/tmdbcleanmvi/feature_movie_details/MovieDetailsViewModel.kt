package com.dpfht.tmdbcleanmvi.feature_movie_details

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.domain.entity.MovieDetailsDomain
import com.dpfht.tmdbcleanmvi.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbcleanmvi.feature_movie_details.MovieDetailsIntent.EnterIdleState
import com.dpfht.tmdbcleanmvi.feature_movie_details.MovieDetailsIntent.FetchDetails
import com.dpfht.tmdbcleanmvi.feature_movie_details.MovieDetailsIntent.NavigateToReviewScreen
import com.dpfht.tmdbcleanmvi.feature_movie_details.MovieDetailsIntent.NavigateToTrailerScreen
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
  private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
  private val navigationService: NavigationService
): BaseViewModel<MovieDetailsIntent, MovieDetailsState>() {

  override val mState = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Idle)

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
          EnterIdleState -> {
            enterIdleState()
          }
        }
      }
    }
  }

  override fun start() {
    if (title.isEmpty()) {
      getMovieDetails()
    } else {
      mState.value = MovieDetailsState.ViewDetails(title, overview, imageUrl)
    }
  }

  private fun getMovieDetails() {
    viewModelScope.launch(Dispatchers.Main) {
      mState.value = MovieDetailsState.IsLoading(true)
      when (val result = getMovieDetailsUseCase(_movieId)) {
        is Success -> {
          onSuccess(result.value)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(result: MovieDetailsDomain) {
    imageUrl = ""
    if (result.imageUrl.isNotEmpty()) {
      imageUrl = result.imageUrl
    }

    _movieId = result.id
    title = result.title
    overview = result.overview

    mState.value = MovieDetailsState.ViewDetails(title, overview, imageUrl)
    mState.value = MovieDetailsState.IsLoading(false)
  }

  private fun onError(message: String) {
    mState.value = MovieDetailsState.IsLoading(false)

    if (message.isNotEmpty()) {
      navigationService.navigateToErrorMessage(message)
    }
  }

  private fun navigateToMovieReviews(movieId: Int, title: String) {
    navigationService.navigateToMovieReviews(movieId, title)
  }

  private fun navigateToMovieTrailer(movieId: Int) {
    navigationService.navigateToMovieTrailer(movieId)
  }

  private fun enterIdleState() {
    viewModelScope.launch {
      mState.value = MovieDetailsState.Idle
    }
  }
}
