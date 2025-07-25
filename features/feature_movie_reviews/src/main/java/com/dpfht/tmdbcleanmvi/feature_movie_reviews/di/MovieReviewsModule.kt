package com.dpfht.tmdbcleanmvi.feature_movie_reviews.di

import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbcleanmvi.domain.model.Review
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieReviewUseCase
import com.dpfht.tmdbcleanmvi.domain.usecase.GetMovieReviewUseCaseImpl
import com.dpfht.tmdbcleanmvi.feature_movie_reviews.adapter.MovieReviewsAdapter
import com.dpfht.tmdbcleanmvi.framework.di.provider.LoadingDialogProvider
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MovieReviewsModule: Module() {

  init {
    bind<GetMovieReviewUseCase>().toClass<GetMovieReviewUseCaseImpl>()
    bind<ArrayList<Review>>().toInstance(arrayListOf())
    bind<MovieReviewsAdapter>().toClass<MovieReviewsAdapter>()
    bind<AlertDialog>().toProvider(LoadingDialogProvider::class)
  }
}
