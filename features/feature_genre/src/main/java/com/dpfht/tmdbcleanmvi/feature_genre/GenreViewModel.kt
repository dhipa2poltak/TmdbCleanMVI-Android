package com.dpfht.tmdbcleanmvi.feature_genre

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.domain.entity.GenreEntity
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Error
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenreViewModel @Inject constructor(
  private val getMovieGenreUseCase: GetMovieGenreUseCase,
  private val genres: ArrayList<GenreEntity>,
  private val navigationService: NavigationService
): BaseViewModel<GenreIntent, GenreState>() {

  override val _state = MutableStateFlow(GenreState())

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
    viewModelScope.launch {
      withContext(Dispatchers.Main) { updateState { it.copy(isLoading = true) } }

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

  private fun onSuccess(genres: List<GenreEntity>) {
    viewModelScope.launch(Dispatchers.Main) {
      for (genre in genres) {
        this@GenreViewModel.genres.add(genre)
        updateState { it.copy(itemInserted = this@GenreViewModel.genres.size - 1) }
      }

      updateState { it.copy(isLoading = false, itemInserted = 0) }
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

  private fun navigateToMoviesByGenre(genreId: Int, genreName: String) {
    navigationService.navigateToMoviesByGender(genreId, genreName)
  }
}
