package com.dpfht.tmdbcleanmvi.feature_movies_by_genre.di

import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbcleanmvi.domain.model.Genre
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieByGenreUseCaseImpl
import com.dpfht.tmdbcleanmvi.framework.di.provider.LoadingDialogProvider
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MoviesByGenreModule: Module() {

  init {
    bind<GetMovieByGenreUseCase>().toClass<GetMovieByGenreUseCaseImpl>()
    bind<ArrayList<Genre>>().toInstance(arrayListOf())
    bind<AlertDialog>().toProvider(LoadingDialogProvider::class)
  }
}
