package com.dpfht.tmdbcleanmvi.feature_movie_reviews

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvi.domain.entity.ReviewEntity
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvi.feature_movie_reviews.MovieReviewsIntent.EnterIdleState
import com.dpfht.tmdbcleanmvi.feature_movie_reviews.MovieReviewsIntent.FetchNextReview
import com.dpfht.tmdbcleanmvi.feature_movie_reviews.MovieReviewsIntent.FetchReview
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieReviewsViewModel @Inject constructor(
  private val getMovieReviewUseCase: GetMovieReviewUseCase,
  private val reviews: ArrayList<ReviewEntity>,
  private val navigationService: NavigationService
): BaseViewModel<MovieReviewsIntent, MovieReviewsState>() {

  override val _state = MutableStateFlow<MovieReviewsState>(MovieReviewsState.Idle)

  private var _movieId = -1
  private var page = 0
  private var isEmptyNextResponse = false

  init {
    handlerIntent()
  }

  private fun handlerIntent() {
    viewModelScope.launch {
      intents.consumeAsFlow().collect { intent ->
        when (intent) {
          FetchReview -> {
            start()
          }
          FetchNextReview -> {
            getMovieReviews()
          }
          EnterIdleState -> {
            enterIdleState()
          }
        }
      }
    }
  }

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  override fun start() {
    if (_movieId != -1 && reviews.isEmpty()) {
      getMovieReviews()
    }
  }

  private fun getMovieReviews() {
    if (isEmptyNextResponse) return

    viewModelScope.launch(Dispatchers.Main) {
      _state.value = MovieReviewsState.IsLoading(true)
      mIsLoadingData = true

      when (val result = getMovieReviewUseCase(_movieId, page + 1)) {
        is Success -> {
          onSuccess(result.value.results, result.value.page)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(reviews: List<ReviewEntity>, page: Int) {
    if (reviews.isNotEmpty()) {
      this.page = page

      for (review in reviews) {
        this.reviews.add(review)
        _state.value = MovieReviewsState.NotifyItemInserted(this.reviews.size - 1)
      }
    } else {
      isEmptyNextResponse = true
    }

    _state.value = MovieReviewsState.IsLoading(false)
    mIsLoadingData = false
  }

  private fun onError(message: String) {
    _state.value = MovieReviewsState.IsLoading(false)
    mIsLoadingData = false

    if (message.isNotEmpty()) {
      navigationService.navigateToErrorMessage(message)
    }
  }

  private fun enterIdleState() {
    viewModelScope.launch {
      _state.value = MovieReviewsState.Idle
    }
  }
}
