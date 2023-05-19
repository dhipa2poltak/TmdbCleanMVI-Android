package com.dpfht.tmdbcleanmvi.feature.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.dpfht.tmdbcleanmvi.R
import com.dpfht.tmdbcleanmvi.databinding.FragmentMovieDetailsBinding
import com.dpfht.tmdbcleanmvi.feature.moviedetails.MovieDetailsState.Idle
import com.dpfht.tmdbcleanmvi.feature.moviedetails.MovieDetailsState.IsLoading
import com.dpfht.tmdbcleanmvi.feature.moviedetails.MovieDetailsState.ViewDetails
import com.dpfht.tmdbcleanmvi.feature.moviedetails.di.MovieDetailsModule
import com.dpfht.tmdbcleanmvi.framework.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import toothpick.config.Module
import toothpick.ktp.delegate.inject
import javax.inject.Inject

class MovieDetailsFragment: BaseFragment<MovieDetailsState>() {

  private lateinit var binding: FragmentMovieDetailsBinding
  private val viewModel by inject<MovieDetailsViewModel>()

  @Inject
  lateinit var loadingDialog: AlertDialog

  override fun getModules(): ArrayList<Module> {
    return arrayListOf(MovieDetailsModule(requireContext()))
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.tvShowReview.setOnClickListener {
      onClickShowReview()
    }

    binding.tvShowTrailer.setOnClickListener {
      onClickShowTrailer()
    }

    lifecycleScope.launch {
      viewModel.state.collect {
        render(it)
      }
    }

    val args = MovieDetailsFragmentArgs.fromBundle(requireArguments())
    val movieId = args.movieId

    viewModel.setMovieId(movieId)

    lifecycleScope.launch {
      viewModel.intents.send(MovieDetailsIntent.FetchDetails)
    }
  }

  override fun render(state: MovieDetailsState) {
    when (state) {
      is IsLoading -> {
        showLoading(state.value)
      }
      is ViewDetails -> {
        showDetails(state.title, state.overview, state.imageUrl)
      }
      Idle -> {

      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    if (isLoading) {
      loadingDialog.show()
    } else {
      loadingDialog.dismiss()
    }
  }

  private fun showDetails(title: String, overview: String, imageUrl: String) {
    binding.tvTitleMovie.text = title
    binding.tvDescMovie.text = overview

    Picasso.get().load(imageUrl)
      .error(android.R.drawable.ic_menu_close_clear_cancel)
      .placeholder(R.drawable.loading)
      .into(binding.ivImageMovie)
  }

  private fun onClickShowReview() {
    lifecycleScope.launch {
      viewModel.intents.send(MovieDetailsIntent.NavigateToReviewScreen)
    }
  }

  private fun onClickShowTrailer() {
    lifecycleScope.launch {
      viewModel.intents.send(MovieDetailsIntent.NavigateToTrailerScreen)
    }
  }

  override fun onPause() {
    super.onPause()
    lifecycleScope.launch {
      viewModel.intents.send(MovieDetailsIntent.EnterIdleState)
    }
  }
}
