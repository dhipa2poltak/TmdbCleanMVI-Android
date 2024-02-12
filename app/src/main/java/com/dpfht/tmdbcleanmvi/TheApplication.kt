package com.dpfht.tmdbcleanmvi

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.dpfht.tmdbcleanmvi.framework.Config
import com.dpfht.tmdbcleanmvi.framework.di.AppModule
import com.dpfht.tmdbcleanmvi.framework.di.NetworkModule
import toothpick.ktp.KTP

class TheApplication: Application() {

  companion object {
    lateinit var instance: TheApplication
  }

  override fun onCreate() {
    Config.BASE_URL = BuildConfig.BASE_URL
    super.onCreate()
    instance = this

    KTP.openRootScope()
      .openSubScope("APPSCOPE")
      .installModules(NetworkModule(), AppModule())
  }

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }
}
