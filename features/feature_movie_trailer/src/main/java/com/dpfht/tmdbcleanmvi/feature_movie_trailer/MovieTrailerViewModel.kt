package com.dpfht.tmdbcleanmvi.feature_movie_trailer

import com.dpfht.tmdbcleanmvi.domain.entity.Result.ErrorResult
import com.dpfht.tmdbcleanmvi.domain.entity.Result.Success
import com.dpfht.tmdbcleanmvi.domain.entity.TrailerEntity
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.tmdbcleanmvi.feature_movie_trailer.MovieTrailerIntent.EnterIdleState
import com.dpfht.tmdbcleanmvi.feature_movie_trailer.MovieTrailerIntent.FetchTrailer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class MovieTrailerViewModel @Inject constructor(
  private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
  private val scope: CoroutineScope
) {

  val intents: Channel<MovieTrailerIntent> = Channel(Channel.UNLIMITED)

  private val _state = MutableStateFlow<MovieTrailerState>(MovieTrailerState.Idle)
  val state: StateFlow<MovieTrailerState>
    get() = _state

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
          EnterIdleState -> {
            enterIdleState()
          }
        }
      }
    }
  }

  private fun start() {
    if (_movieId != -1) {
      getMovieTrailer()
    }
  }

  private fun getMovieTrailer() {
    scope.launch(Dispatchers.Main) {
      when (val result = getMovieTrailerUseCase(_movieId)) {
        is Success -> {
          onSuccess(result.value.results)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(trailers: List<TrailerEntity>) {
    var keyVideo = ""
    for (trailer in trailers) {
      if (trailer.site.lowercase(Locale.ROOT).trim() == "youtube"
      ) {
        keyVideo = trailer.key
        break
      }
    }

    if (keyVideo.isNotEmpty()) {
      _state.value = MovieTrailerState.ViewTrailer(keyVideo)
    }
  }

  private fun onError(message: String) {
    _state.value = MovieTrailerState.ErrorMessage(message)
  }

  private fun enterIdleState() {
    scope.launch {
      _state.value = MovieTrailerState.Idle
    }
  }
}