package com.dpfht.tmdbcleanmvi.feature_movie_trailer.di

import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieTrailerUseCaseImpl
import kotlinx.coroutines.Job
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MovieTrailerModule: Module() {

  init {
    bind<GetMovieTrailerUseCase>().toClass<GetMovieTrailerUseCaseImpl>()
    bind<Job>().toInstance(Job())
  }
}
