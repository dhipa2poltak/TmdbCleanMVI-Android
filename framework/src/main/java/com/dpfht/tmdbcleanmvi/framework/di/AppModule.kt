package com.dpfht.tmdbcleanmvi.framework.di

import com.dpfht.tmdbcleanmvi.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvi.data.repository.AppRepositoryImpl
import com.dpfht.tmdbcleanmvi.domain.repository.AppRepository
import com.dpfht.tmdbcleanmvi.framework.data.datasource.RemoteDataSourceImpl
import toothpick.config.Module
import toothpick.ktp.binding.bind

class AppModule: Module() {

  init {
    bind<AppDataSource>().toClass(RemoteDataSourceImpl::class)
    bind<AppRepository>().toClass(AppRepositoryImpl::class)
  }
}
