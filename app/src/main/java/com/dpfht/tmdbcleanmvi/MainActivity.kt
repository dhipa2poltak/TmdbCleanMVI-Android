package com.dpfht.tmdbcleanmvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.dpfht.tmdbcleanmvi.databinding.ActivityMainBinding
import com.dpfht.tmdbcleanmvi.navigation.di.NavigationModule
import com.dpfht.tmdbcleanmvi.navigation.R as navigationR
import toothpick.ktp.KTP

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val appBarConfiguration = AppBarConfiguration(
      setOf(navigationR.id.genreFragment)
    )

    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.demo_nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

    KTP.openRootScope()
      .openSubScope("APPSCOPE")
      .openSubScope("ActivityScope")
      .installModules(NavigationModule(navController, this))
      .inject(this)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }
}
