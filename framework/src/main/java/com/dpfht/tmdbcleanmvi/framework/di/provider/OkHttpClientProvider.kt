package com.dpfht.tmdbcleanmvi.framework.di.provider

import android.content.Context
import com.dpfht.tmdbcleanmvi.framework.BuildConfig
import com.dpfht.tmdbcleanmvi.framework.data.datasource.remote.rest.AuthInterceptor
import com.dpfht.tmdbcleanmvi.framework.data.datasource.remote.rest.UnsafeOkHttpClient
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Provider

class OkHttpClientProvider @Inject constructor(
  val context: Context,
  private val certificatePinner: CertificatePinner
): Provider<OkHttpClient> {

  override fun get(): OkHttpClient {
    if (BuildConfig.DEBUG) {
      return UnsafeOkHttpClient.getUnsafeOkHttpClient(context)
    }

    val httpClientBuilder = OkHttpClient()
      .newBuilder()
      .certificatePinner(certificatePinner)

    httpClientBuilder.addInterceptor(AuthInterceptor())

    return httpClientBuilder.build()
  }
}
