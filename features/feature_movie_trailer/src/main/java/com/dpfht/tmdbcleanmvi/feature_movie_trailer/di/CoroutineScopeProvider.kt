package com.dpfht.tmdbcleanmvi.feature_movie_trailer.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import javax.inject.Inject
import javax.inject.Provider

class CoroutineScopeProvider @Inject constructor(
  private val job: Job
): Provider<CoroutineScope> {

  override fun get(): CoroutineScope {
    return CoroutineScope(job)
  }
}
