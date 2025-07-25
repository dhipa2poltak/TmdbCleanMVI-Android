package com.dpfht.tmdbcleanmvi.feature_genre

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.domain.model.Genre
import com.dpfht.tmdbcleanmvi.domain.model.Result.Error
import com.dpfht.tmdbcleanmvi.domain.model.Result.Success
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbcleanmvi.feature_genre.adapter.GenreAdapter
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenreViewModel @Inject constructor(
  private val getMovieGenreUseCase: GetMovieGenreUseCase,
  private val genres: ArrayList<Genre>,
  val adapter: GenreAdapter,
  private val navigationService: NavigationService
): BaseViewModel<GenreIntent, GenreState>(GenreState()) {

  init {
    handlerIntent()
  }

  private fun handlerIntent() {
    viewModelScope.launch {
      intents.consumeAsFlow().collect { genreIntent ->
        when (genreIntent) {
          GenreIntent.FetchGenre -> {
            start()
          }
          is GenreIntent.NavigateToNextScreen -> {
            navigateToMoviesByGenre(genres[genreIntent.position].id, genres[genreIntent.position].name)
          }
        }
      }
    }
  }

  override fun start() {
    if (genres.isEmpty()) {
      getMovieGenre()
    }
  }

  private fun getMovieGenre() {
    updateState { it.copy(isLoading = true) }

    viewModelScope.launch {
      when (val result = getMovieGenreUseCase()) {
        is Success -> {
          onSuccess(result.value.genres)
        }
        is Error -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(genres: List<Genre>) {
    for (genre in genres) {
      this@GenreViewModel.genres.add(genre)
      adapter.notifyItemInserted(this@GenreViewModel.genres.size - 1)
    }

    updateState { it.copy(isLoading = false) }
  }

  private fun onError(message: String) {
    updateState { it.copy(isLoading = false) }

    if (message.isNotEmpty()) {
      navigationService.navigateToErrorMessage(message)
    }
  }

  private fun navigateToMoviesByGenre(genreId: Int, genreName: String) {
    navigationService.navigateToMoviesByGender(genreId, genreName)
  }
}
