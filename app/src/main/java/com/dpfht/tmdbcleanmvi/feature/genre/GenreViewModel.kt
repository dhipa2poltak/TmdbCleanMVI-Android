package com.dpfht.tmdbcleanmvi.feature.genre

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.domain.entity.GenreEntity
import com.dpfht.tmdbcleanmvi.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenreViewModel @Inject constructor(
  private val getMovieGenreUseCase: GetMovieGenreUseCase,
  private val genres: ArrayList<GenreEntity>,
  private val navigationService: NavigationService
): BaseViewModel<GenreIntent, GenreState>() {

  override val mState = MutableStateFlow<GenreState>(GenreState.Idle)

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
          GenreIntent.EnterIdleState -> {
            enterIdleState()
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
      mState.value = GenreState.IsLoading(true)

      when (val result = getMovieGenreUseCase()) {
        is Success -> {
          onSuccess(result.value.genres)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(genres: List<GenreEntity>) {
    for (genre in genres) {
      this.genres.add(genre)
      mState.value = GenreState.NotifyItemInserted(this.genres.size - 1)
    }

    mState.value = GenreState.IsLoading(false)
  }

  private fun onError(message: String) {
    mState.value = GenreState.IsLoading(false)

    if (message.isNotEmpty()) {
      navigationService.navigateToErrorMessage(message)
    }
  }

  private fun navigateToMoviesByGenre(genreId: Int, genreName: String) {
    navigationService.navigateToMoviesByGender(genreId, genreName)
  }

  private fun enterIdleState() {
    viewModelScope.launch {
      mState.value = GenreState.Idle
    }
  }
}
