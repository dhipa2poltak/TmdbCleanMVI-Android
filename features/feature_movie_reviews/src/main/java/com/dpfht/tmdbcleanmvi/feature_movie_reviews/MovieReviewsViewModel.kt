package com.dpfht.tmdbcleanmvi.feature_movie_reviews

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.domain.model.Result.Error
import com.dpfht.tmdbcleanmvi.domain.model.Result.Success
import com.dpfht.tmdbcleanmvi.domain.model.Review
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvi.feature_movie_reviews.MovieReviewsIntent.FetchNextReview
import com.dpfht.tmdbcleanmvi.feature_movie_reviews.MovieReviewsIntent.FetchReview
import com.dpfht.tmdbcleanmvi.feature_movie_reviews.adapter.MovieReviewsAdapter
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieReviewsViewModel @Inject constructor(
  private val getMovieReviewUseCase: GetMovieReviewUseCase,
  private val reviews: ArrayList<Review>,
  val adapter: MovieReviewsAdapter,
  private val navigationService: NavigationService
): BaseViewModel<MovieReviewsIntent, MovieReviewsState>(MovieReviewsState()) {

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
        }
      }
    }
  }

  fun setMovieId(movieId: Int) {
    updateState { it.copy(movieId = movieId) }
  }

  override fun start() {
    if (state.value.movieId != -1 && reviews.isEmpty()) {
      getMovieReviews()
    }
  }

  private fun getMovieReviews() {
    if (state.value.isEmptyNextResponse) return

    updateState { it.copy(isLoading = true) }
    viewModelScope.launch {
      mIsLoadingData = true

      when (val result = getMovieReviewUseCase(state.value.movieId, state.value.page + 1)) {
        is Success -> {
          onSuccess(result.value.results, result.value.page)
        }
        is Error -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(reviews: List<Review>, page: Int) {
    if (reviews.isNotEmpty()) {
      updateState { it.copy(page = page) }

      for (review in reviews) {
        this@MovieReviewsViewModel.reviews.add(review)
        adapter.notifyItemInserted(this@MovieReviewsViewModel.reviews.size - 1)
      }
    } else {
      if (this@MovieReviewsViewModel.reviews.isEmpty()) {
        updateState { it.copy(isNoReviews = true) }
      }

      updateState { it.copy(isEmptyNextResponse = true) }
    }

    updateState { it.copy(isLoading = false) }
    mIsLoadingData = false
  }

  private fun onError(message: String) {
    updateState { it.copy(isLoading = false) }
    mIsLoadingData = false

    if (message.isNotEmpty()) {
      navigationService.navigateToErrorMessage(message)
    }
  }
}
