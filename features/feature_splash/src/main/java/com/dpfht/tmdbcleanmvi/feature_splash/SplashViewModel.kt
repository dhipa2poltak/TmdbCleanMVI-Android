package com.dpfht.tmdbcleanmvi.feature_splash

import androidx.lifecycle.viewModelScope
import com.dpfht.tmdbcleanmvi.framework.base.BaseViewModel
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
  private val navigationService: NavigationService
): BaseViewModel<SplashIntent, SplashState>() {

  override val _state = MutableStateFlow(SplashState())

  init {
    handlerIntent()
  }

  private fun handlerIntent() {
    viewModelScope.launch {
      intents.consumeAsFlow().collect { splashIntent ->
        when (splashIntent) {
          SplashIntent.Init -> {
            start()
          }
        }
      }
    }
  }

  override fun start() {
    viewModelScope.launch {
      delay(3000)
      navigationService.navigateToGender()
    }
  }
}
