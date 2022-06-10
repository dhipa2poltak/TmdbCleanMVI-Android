package com.dpfht.tmdbcleanmvi.feature.moviereviews.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbcleanmvi.core.data.model.remote.Review
import com.dpfht.tmdbcleanmvi.core.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvi.core.usecase.GetMovieReviewUseCaseImpl
import com.dpfht.tmdbcleanmvi.feature.common.LoadingDialogProvider
import com.dpfht.tmdbcleanmvi.feature.moviereviews.adapter.MovieReviewsAdapter
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MovieReviewsModule(context: Context): Module() {

  init {
    bind<Context>().toInstance(context)
    bind<GetMovieReviewUseCase>().toClass<GetMovieReviewUseCaseImpl>()
    bind<ArrayList<Review>>().toInstance(arrayListOf())
    bind<MovieReviewsAdapter>().toClass<MovieReviewsAdapter>()
    bind<AlertDialog>().toProvider(LoadingDialogProvider::class)
  }
}
