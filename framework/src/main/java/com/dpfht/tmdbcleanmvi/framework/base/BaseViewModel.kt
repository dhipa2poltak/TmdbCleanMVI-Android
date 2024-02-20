package com.dpfht.tmdbcleanmvi.framework.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<VI, VS>(viewState: VS): ViewModel() {

  val intents: Channel<VI> = Channel(Channel.UNLIMITED)

  protected val _state = MutableStateFlow(viewState)
  val state: StateFlow<VS> = _state

  protected var mIsLoadingData = false

  fun isLoadingData() = mIsLoadingData

  protected abstract fun start()

  protected fun updateState(handler: (intent: VS) -> VS) {
    _state.value = handler(state.value)
  }
}
