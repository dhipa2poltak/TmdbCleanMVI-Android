package com.dpfht.tmdbcleanmvi.feature_movie_trailer

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Error
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvi.domain.entity.TrailerEntity
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.tmdbcleanmvi.feature_movie_trailer.MovieTrailerIntent.FetchTrailer
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class MovieTrailerViewModel @Inject constructor(
  private val getMovieTrailerUseCase: GetMovieTrailerUseCase
): BaseViewModel<MovieTrailerIntent, MovieTrailerState>() {

  override val _state = MutableStateFlow(MovieTrailerState())

  fun setMovieId(movieId: Int) {
    viewModelScope.launch {
      updateState { it.copy(movieId = movieId) }
    }
  }

  init {
    handlerIntent()
  }

  private fun handlerIntent() {
    viewModelScope.launch {
      intents.consumeAsFlow().collect { intent ->
        when (intent) {
          FetchTrailer -> {
            start()
          }
        }
      }
    }
  }

  override fun start() {
    if (state.value.movieId != -1) {
      getMovieTrailer()
    }
  }

  private fun getMovieTrailer() {
    viewModelScope.launch {
      when (val result = getMovieTrailerUseCase(state.value.movieId)) {
        is Success -> {
          onSuccess(result.value.results)
        }
        is Error -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(trailers: List<TrailerEntity>) {
    viewModelScope.launch(Dispatchers.Main) {
      var keyVideo = ""
      for (trailer in trailers) {
        if (trailer.site.lowercase(Locale.ROOT).trim() == "youtube"
        ) {
          keyVideo = trailer.key
          break
        }
      }

      if (keyVideo.isNotEmpty()) {
        updateState { it.copy(keyVideo = keyVideo) }
      }
    }
  }

  private fun onError(message: String) {
    viewModelScope.launch(Dispatchers.Main) {
      updateState { it.copy(errorMessage = message) }
    }
  }
}
