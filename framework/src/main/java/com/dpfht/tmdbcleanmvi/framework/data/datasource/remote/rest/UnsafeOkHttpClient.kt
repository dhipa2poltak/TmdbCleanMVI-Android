package com.dpfht.tmdbcleanmvi.framework.data.datasource.remote.rest

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object UnsafeOkHttpClient {

  fun getUnsafeOkHttpClient(context: Context): OkHttpClient {
    return try {
      // Create a trust manager that does not validate certificate chains
      val trustAllCerts =
        arrayOf<TrustManager>(
          object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
              chain: Array<X509Certificate?>?,
              authType: String?
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
              chain: Array<X509Certificate?>?,
              authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
              return arrayOf()
            }

            /*
            val acceptedIssuers: Array<X509Certificate?>?
              get() = arrayOf()
            */
          }
        )

      // Install the all-trusting trust manager
      val sslContext = SSLContext.getInstance("SSL")
      sslContext.init(null, trustAllCerts, SecureRandom())
      // Create an ssl socket factory with our all-trusting manager
      val sslSocketFactory = sslContext.socketFactory
      val builder = OkHttpClient.Builder()
      builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
      builder.hostnameVerifier { _, _ -> true }

      val httpLoggingInterceptor = HttpLoggingInterceptor()
      httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

      val chuckerInterceptor = ChuckerInterceptor.Builder(context)
        .collector(ChuckerCollector(context))
        .maxContentLength(250000L)
        .redactHeaders("Authorization"/*, "Bearer"*/) // Optional
        .alwaysReadResponseBody(true)
        .build()

      builder.addInterceptor(AuthInterceptor())
      builder.addInterceptor(chuckerInterceptor)
      builder.addInterceptor(httpLoggingInterceptor)

      builder.build()
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }
}
