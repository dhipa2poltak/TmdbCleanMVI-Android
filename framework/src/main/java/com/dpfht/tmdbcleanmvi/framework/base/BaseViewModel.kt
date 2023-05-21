package com.dpfht.tmdbcleanmvi.framework.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<VI, VS>: ViewModel() {

  val intents: Channel<VI> = Channel(Channel.UNLIMITED)

  protected abstract val _state: MutableStateFlow<VS>
  val state: StateFlow<VS>
    get() = _state

  protected var mIsLoadingData = false

  fun isLoadingData() = mIsLoadingData

  protected abstract fun start()

  protected suspend fun updateState(handler: suspend (intent: VS) -> VS) {
    _state.value = handler(state.value)
  }
}
