package com.dpfht.tmdbcleanmvi.feature_splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dpfht.tmdbcleanmvi.feature_splash.databinding.FragmentSplashBinding
import com.dpfht.tmdbcleanmvi.framework.base.BaseFragment
import kotlinx.coroutines.launch
import toothpick.config.Module
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class SplashFragment: BaseFragment<FragmentSplashBinding, SplashState>(R.layout.fragment_splash) {

  private val viewModel by inject<SplashViewModel>()

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    KTP.openRootScope()
      .openSubScope("APPSCOPE")
      .openSubScope("ActivityScope")
      .inject(this)

    lifecycleScope.launch {
      viewModel.state.collect {
        render(it)
      }
    }

    lifecycleScope.launch {
      viewModel.intents.send(SplashIntent.Init)
    }
  }

  override fun getModules(): ArrayList<Module> {
    return arrayListOf()
  }

  override fun render(state: SplashState) {}

  override fun onStart() {
    super.onStart()
    (requireActivity() as AppCompatActivity).supportActionBar?.hide()
  }

  override fun onStop() {
    super.onStop()
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
  }
}
