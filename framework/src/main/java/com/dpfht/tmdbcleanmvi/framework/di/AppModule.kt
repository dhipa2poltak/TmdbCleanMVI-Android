package com.dpfht.tmdbcleanmvi.framework.di

import android.app.Application
import android.content.Context
import com.dpfht.tmdbcleanmvi.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvi.data.repository.AppRepositoryImpl
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvi.framework.data.datasource.remote.RemoteDataSourceImpl
import toothpick.config.Module
import toothpick.ktp.binding.bind

class AppModule(
  application: Application
): Module() {

  init {
    bind<Context>().toInstance(application.applicationContext)
    bind<AppDataSource>().toClass(RemoteDataSourceImpl::class)
    bind<AppRepository>().toClass(AppRepositoryImpl::class)
  }
}
