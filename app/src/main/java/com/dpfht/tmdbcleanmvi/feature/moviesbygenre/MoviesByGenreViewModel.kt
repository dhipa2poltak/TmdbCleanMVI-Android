package com.dpfht.tmdbcleanmvi.feature.moviesbygenre

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvi.core.domain.entity.MovieEntity
import com.dpfht.tmdbcleanmvi.core.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvi.core.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvi.core.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvi.feature.moviesbygenre.MoviesByGenreIntent.EnterIdleState
import com.dpfht.tmdbcleanmvi.feature.moviesbygenre.MoviesByGenreIntent.FetchMovie
import com.dpfht.tmdbcleanmvi.feature.moviesbygenre.MoviesByGenreIntent.FetchNextMovie
import com.dpfht.tmdbcleanmvi.feature.moviesbygenre.MoviesByGenreIntent.NavigateToNextScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesByGenreViewModel @Inject constructor(
  private val getMovieByGenreUseCase: GetMovieByGenreUseCase,
  private val movies: ArrayList<MovieEntity>
): BaseViewModel<MoviesByGenreIntent, MoviesByGenreState>() {

  override val mState = MutableStateFlow<MoviesByGenreState>(MoviesByGenreState.Idle)

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
            navigateToNextScreen(movies[intent.position].id)
          }
          EnterIdleState -> {
            enterIdleState()
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

    viewModelScope.launch(Dispatchers.Main) {
      mState.value = MoviesByGenreState.IsLoading(true)
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
    if (movies.isNotEmpty()) {
      this.page = page

      for (movie in movies) {
        this.movies.add(movie)
        mState.value = MoviesByGenreState.NotifyItemInserted(this.movies.size - 1)
      }
    } else {
      isEmptyNextResponse = true
    }

    mState.value = MoviesByGenreState.IsLoading(false)
    mIsLoadingData = false
  }

  private fun onError(message: String) {
    mState.value = MoviesByGenreState.IsLoading(false)
    mIsLoadingData = false
    mState.value = MoviesByGenreState.ErrorMessage(message)
  }

  private fun navigateToNextScreen(movieId: Int) {
    viewModelScope.launch {
      mState.value = MoviesByGenreState.NavigateToNextScreen(
        MoviesByGenreFragmentDirections.actionMovieByGenreToMovieDetails(movieId)
      )
    }
  }

  private fun enterIdleState() {
    viewModelScope.launch {
      mState.value = MoviesByGenreState.Idle
    }
  }
}
