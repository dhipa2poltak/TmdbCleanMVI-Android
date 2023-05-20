package com.dpfht.tmdbcleanmvi.feature_splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dpfht.tmdbcleanmvi.feature_splash.databinding.FragmentSplashBinding
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import toothpick.ktp.KTP
import javax.inject.Inject

class SplashFragment: Fragment() {

  private lateinit var binding: FragmentSplashBinding

  @Inject
  lateinit var navigationService: NavigationService

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    KTP.openRootScope()
      .openSubScope("APPSCOPE")
      .openSubScope("ActivityScope")
      .inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentSplashBinding.inflate(inflater, container, false)

    return binding.root
  }

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
