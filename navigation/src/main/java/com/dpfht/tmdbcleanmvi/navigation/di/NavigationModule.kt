package com.dpfht.tmdbcleanmvi.navigation.di

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.dpfht.tmdbcleanmvi.framework.navigation.NavigationService
import com.dpfht.tmdbcleanmvi.navigation.NavigationServiceImpl
import toothpick.config.Module
import toothpick.ktp.binding.bind

class NavigationModule(
  navController: NavController,
  activity: AppCompatActivity
): Module() {

  init {
    bind<NavController>().toInstance(navController)
    bind<AppCompatActivity>().toInstance(activity)
    bind<NavigationService>().toClass<NavigationServiceImpl>().singleton()
  }
}
