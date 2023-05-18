package com.dpfht.tmdbcleanmvi.feature.moviesbygenre.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbcleanmvi.domain.entity.GenreEntity
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieByGenreUseCaseImpl
import com.dpfht.tmdbcleanmvi.framework.common.LoadingDialogProvider
import com.dpfht.tmdbcleanmvi.feature.genre.adapter.GenreAdapter
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MoviesByGenreModule(context: Context): Module() {

  init {
    bind<Context>().toInstance(context)
    bind<GetMovieByGenreUseCase>().toClass<GetMovieByGenreUseCaseImpl>()
    bind<ArrayList<GenreEntity>>().toInstance(arrayListOf())
    bind<GenreAdapter>().toClass<GenreAdapter>()
    bind<AlertDialog>().toProvider(LoadingDialogProvider::class)
  }
}
