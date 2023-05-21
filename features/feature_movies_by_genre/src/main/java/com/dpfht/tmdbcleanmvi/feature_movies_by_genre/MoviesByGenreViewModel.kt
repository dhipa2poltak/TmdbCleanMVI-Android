package com.dpfht.tmdbcleanmvi.feature_movies_by_genre

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.domain.entity.MovieEntity
import com.dpfht.tmdbcleanmvi.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.MoviesByGenreIntent.FetchMovie
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.MoviesByGenreIntent.FetchNextMovie
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.MoviesByGenreIntent.NavigateToNextScreen
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesByGenreViewModel @Inject constructor(
  private val getMovieByGenreUseCase: GetMovieByGenreUseCase,
  private val movies: ArrayList<MovieEntity>,
  private val navigationService: NavigationService
): BaseViewModel<MoviesByGenreIntent, MoviesByGenreState>() {

  override val _state = MutableStateFlow(MoviesByGenreState())

  private var _genreId = -1
  private var page = 0
  private var isEmptyNextResponse = false

  init {
    handlerIntent()
  }

  private fun handlerIntent() {
    viewModelScope.launch {
      intents.consumeAsFlow().collect { intent ->
        when (intent) {
          FetchMovie -> {
            start()
          }
          FetchNextMovie -> {
            getMoviesByGenre()
          }
          is NavigateToNextScreen -> {
            navigateToMovieDetails(movies[intent.position].id)
          }
        }
      }
    }
  }

  fun setGenreId(genreId: Int) {
    this._genreId = genreId
  }

  override fun start() {
    if (_genreId != -1 && movies.isEmpty()) {
      getMoviesByGenre()
    }
  }

  private fun getMoviesByGenre() {
    if (isEmptyNextResponse) return

    viewModelScope.launch {
      withContext(Dispatchers.Main) { updateState { it.copy(isLoading = true) } }
      mIsLoadingData = true

      when (val result = getMovieByGenreUseCase(_genreId, page + 1)) {
        is Success -> {
          onSuccess(result.value.results, result.value.page)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(movies: List<MovieEntity>, page: Int) {
    viewModelScope.launch(Dispatchers.Main) {
      if (movies.isNotEmpty()) {
        this@MoviesByGenreViewModel.page = page

        for (movie in movies) {
          this@MoviesByGenreViewModel.movies.add(movie)
          updateState { it.copy(itemInserted = this@MoviesByGenreViewModel.movies.size - 1) }
        }
      } else {
        isEmptyNextResponse = true
      }

      updateState { it.copy(isLoading = false, itemInserted = 0) }
      mIsLoadingData = false
    }
  }

  private fun onError(message: String) {
    viewModelScope.launch(Dispatchers.Main) {
      updateState { it.copy(isLoading = false) }
      mIsLoadingData = false

      if (message.isNotEmpty()) {
        navigationService.navigateToErrorMessage(message)
      }
    }
  }

  private fun navigateToMovieDetails(movieId: Int) {
    navigationService.navigateToMovieDetails(movieId)
  }
}
