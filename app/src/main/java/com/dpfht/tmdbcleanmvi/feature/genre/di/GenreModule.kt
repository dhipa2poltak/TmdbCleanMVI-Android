package com.dpfht.tmdbcleanmvi.feature.genre.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbcleanmvi.core.data.model.remote.Genre
import com.dpfht.tmdbcleanmvi.core.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbcleanmvi.core.domain.usecase.GetMovieGenreUseCaseImpl
import com.dpfht.tmdbcleanmvi.framework.common.LoadingDialogProvider
import com.dpfht.tmdbcleanmvi.feature.genre.adapter.GenreAdapter
import toothpick.config.Module
import toothpick.ktp.binding.bind

class GenreModule(context: Context): Module() {

  init {
    bind<Context>().toInstance(context)
    bind<GetMovieGenreUseCase>().toClass<GetMovieGenreUseCaseImpl>()
    bind<ArrayList<Genre>>().toInstance(arrayListOf())
    bind<GenreAdapter>().toClass<GenreAdapter>()
    bind<AlertDialog>().toProvider(LoadingDialogProvider::class)
  }
}
