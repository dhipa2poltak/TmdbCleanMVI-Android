package com.dpfht.tmdbcleanmvi.feature.moviedetails.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbcleanmvi.core.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.tmdbcleanmvi.core.domain.usecase.GetMovieDetailsUseCaseImpl
import com.dpfht.tmdbcleanmvi.feature.common.LoadingDialogProvider
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MovieDetailsModule(context: Context): Module() {

  init {
    bind<Context>().toInstance(context)
    bind<GetMovieDetailsUseCase>().toClass<GetMovieDetailsUseCaseImpl>()
    bind<AlertDialog>().toProvider(LoadingDialogProvider::class)
  }
}
