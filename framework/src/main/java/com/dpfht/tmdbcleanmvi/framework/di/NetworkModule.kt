package com.dpfht.tmdbcleanmvi.framework.di

import com.dpfht.tmdbcleanmvi.framework.data.datasource.remote.rest.RestService
import com.dpfht.tmdbcleanmvi.framework.di.provider.CertificatePinnerProvider
import com.dpfht.tmdbcleanmvi.framework.di.provider.HttpLoggingInterceptorProvider
import com.dpfht.tmdbcleanmvi.framework.di.provider.OkHttpClientProvider
import com.dpfht.tmdbcleanmvi.framework.di.provider.RestServiceProvider
import com.dpfht.tmdbcleanmvi.framework.di.provider.RetrofitProvider
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import toothpick.config.Module
import toothpick.ktp.binding.bind

class NetworkModule: Module() {

  init {
    bind<CertificatePinner>().toProvider(CertificatePinnerProvider::class)
    bind<HttpLoggingInterceptor>().toProvider(HttpLoggingInterceptorProvider::class)
    bind<OkHttpClient>().toProvider(OkHttpClientProvider::class)
    bind<Retrofit>().toProvider(RetrofitProvider::class)
    bind<RestService>().toProvider(RestServiceProvider::class)
  }
}
