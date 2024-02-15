package com.dpfht.tmdbcleanmvi.feature_genre.di

import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbcleanmvi.domain.entity.GenreEntity
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieGenreUseCase
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieGenreUseCaseImpl
import com.dpfht.tmdbcleanmvi.feature_genre.adapter.GenreAdapter
import com.dpfht.tmdbcleanmvi.framework.di.provider.LoadingDialogProvider
import toothpick.config.Module
import toothpick.ktp.binding.bind

class GenreModule: Module() {

  init {
    bind<GetMovieGenreUseCase>().toClass<GetMovieGenreUseCaseImpl>()
    bind<ArrayList<GenreEntity>>().toInstance(arrayListOf())
    bind<GenreAdapter>().toClass<GenreAdapter>()
    bind<AlertDialog>().toProvider(LoadingDialogProvider::class)
  }
}
