package com.dpfht.tmdbcleanmvi.feature_movie_trailer

import com.dpfht.tmdbcleanmvi.domain.entity.Result.Error
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvi.domain.entity.TrailerEntity
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.tmdbcleanmvi.feature_movie_trailer.MovieTrailerIntent.FetchTrailer
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class MovieTrailerViewModel @Inject constructor(
  private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
  private val scope: CoroutineScope
): BaseViewModel<MovieTrailerIntent, MovieTrailerState>() {

  override val _state = MutableStateFlow(MovieTrailerState())
  private var _movieId = -1

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  init {
    handlerIntent()
  }

  private fun handlerIntent() {
    scope.launch {
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
    if (_movieId != -1) {
      getMovieTrailer()
    }
  }

  private fun getMovieTrailer() {
    scope.launch {
      when (val result = getMovieTrailerUseCase(_movieId)) {
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
    scope.launch(Dispatchers.Main) {
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
    scope.launch(Dispatchers.Main) {
      updateState { it.copy(errorMessage = message) }
    }
  }
}
