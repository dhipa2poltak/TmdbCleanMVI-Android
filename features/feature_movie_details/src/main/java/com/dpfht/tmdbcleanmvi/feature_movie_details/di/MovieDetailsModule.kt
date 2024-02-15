package com.dpfht.tmdbcleanmvi.feature_movie_details.di

import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieDetailsUseCaseImpl
import com.dpfht.tmdbcleanmvi.framework.di.provider.LoadingDialogProvider
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MovieDetailsModule: Module() {

  init {
    bind<GetMovieDetailsUseCase>().toClass<GetMovieDetailsUseCaseImpl>()
    bind<AlertDialog>().toProvider(LoadingDialogProvider::class)
  }
}
