package com.dpfht.tmdbcleanmvi.framework.di.provider

import com.dpfht.tmdbcleanmvi.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class RetrofitProvider @Inject constructor(
  private val okHttpClient: OkHttpClient
): Provider<Retrofit> {
  override fun get(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
  }
}
