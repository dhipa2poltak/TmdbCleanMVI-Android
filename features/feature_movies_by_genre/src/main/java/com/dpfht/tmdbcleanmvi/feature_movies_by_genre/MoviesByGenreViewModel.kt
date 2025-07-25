package com.dpfht.tmdbcleanmvi.feature_movies_by_genre

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.domain.model.Movie
import com.dpfht.tmdbcleanmvi.domain.model.Result.Error
import com.dpfht.tmdbcleanmvi.domain.model.Result.Success
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.MoviesByGenreIntent.FetchMovie
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.MoviesByGenreIntent.FetchNextMovie
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.MoviesByGenreIntent.NavigateToNextScreen
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.adapter.MoviesByGenreAdapter
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesByGenreViewModel @Inject constructor(
  private val getMovieByGenreUseCase: GetMovieByGenreUseCase,
  private val movies: ArrayList<Movie>,
  val adapter: MoviesByGenreAdapter,
  private val navigationService: NavigationService
): BaseViewModel<MoviesByGenreIntent, MoviesByGenreState>(MoviesByGenreState()) {

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
    updateState { it.copy(genreId = genreId) }
  }

  override fun start() {
    if (state.value.genreId != -1 && movies.isEmpty()) {
      getMoviesByGenre()
    }
  }

  private fun getMoviesByGenre() {
    if (state.value.isEmptyNextResponse) return

    updateState { it.copy(isLoading = true) }
    viewModelScope.launch {
      mIsLoadingData = true

      when (val result = getMovieByGenreUseCase(state.value.genreId, state.value.page + 1)) {
        is Success -> {
          onSuccess(result.value.results, result.value.page)
        }
        is Error -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(movies: List<Movie>, page: Int) {
    if (movies.isNotEmpty()) {
      updateState { it.copy(page = page) }

      for (movie in movies) {
        this@MoviesByGenreViewModel.movies.add(movie)
        adapter.notifyItemInserted(this@MoviesByGenreViewModel.movies.size - 1)
      }
    } else {
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

  private fun navigateToMovieDetails(movieId: Int) {
    navigationService.navigateToMovieDetails(movieId)
  }
}
