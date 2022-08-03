package com.dpfht.tmdbcleanmvi.framework.di

import com.dpfht.tmdbcleanmvi.core.data.repository.AppDataSource
import com.dpfht.tmdbcleanmvi.core.data.repository.AppRepository
import com.dpfht.tmdbcleanmvi.framework.rest.api.AppRepositoryImpl
import com.dpfht.tmdbcleanmvi.framework.rest.api.RemoteDataSourceImpl
import toothpick.config.Module
import toothpick.ktp.binding.bind

class AppModule: Module() {

  init {
    bind<AppDataSource>().toClass(RemoteDataSourceImpl::class)
    bind<AppRepository>().toClass(AppRepositoryImpl::class)
  }
}