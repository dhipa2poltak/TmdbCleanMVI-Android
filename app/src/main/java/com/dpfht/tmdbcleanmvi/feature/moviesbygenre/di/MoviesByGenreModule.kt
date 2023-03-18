package com.dpfht.tmdbcleanmvi.feature.moviesbygenre.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbcleanmvi.core.data.model.remote.Genre
import com.dpfht.tmdbcleanmvi.core.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvi.core.usecase.GetMovieByGenreUseCaseImpl
import com.dpfht.tmdbcleanmvi.feature.common.LoadingDialogProvider
import com.dpfht.tmdbcleanmvi.feature.genre.adapter.GenreAdapter
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MoviesByGenreModule(context: Context): Module() {

  init {
    bind<Context>().toInstance(context)
    bind<GetMovieByGenreUseCase>().toClass<GetMovieByGenreUseCaseImpl>()
    bind<ArrayList<Genre>>().toInstance(arrayListOf())
    bind<GenreAdapter>().toClass<GenreAdapter>()
    bind<AlertDialog>().toProvider(LoadingDialogProvider::class)
  }
}
