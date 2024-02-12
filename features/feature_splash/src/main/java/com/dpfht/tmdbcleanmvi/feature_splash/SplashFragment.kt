package com.dpfht.tmdbcleanmvi.feature_splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dpfht.tmdbcleanmvi.feature_splash.databinding.FragmentSplashBinding
import com.dpfht.tmdbcleanmvi.framework.base.BaseFragment
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import toothpick.config.Module
import toothpick.ktp.KTP
import javax.inject.Inject

class SplashFragment: BaseFragment<FragmentSplashBinding, SplashState>(R.layout.fragment_splash) {

  @Inject
  lateinit var navigationService: NavigationService

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    KTP.openRootScope()
      .openSubScope("APPSCOPE")
      .openSubScope("ActivityScope")
      .inject(this)
  }

  override fun getModules(): ArrayList<Module> {
    return arrayListOf()
  }

  override fun render(state: SplashState) {}

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
      navigateToNextScreen()
    }, 3000)
  }

  private fun navigateToNextScreen() {
    navigationService.navigateToGender()
  }

  override fun onStart() {
    super.onStart()
    (requireActivity() as AppCompatActivity).supportActionBar?.hide()
  }

  override fun onStop() {
    super.onStop()
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
  }
}
