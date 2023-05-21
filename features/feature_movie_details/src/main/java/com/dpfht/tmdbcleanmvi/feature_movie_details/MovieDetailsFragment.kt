package com.dpfht.tmdbcleanmvi.feature_movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.dpfht.tmdbcleanmvi.feature_movie_details.databinding.FragmentMovieDetailsBinding
import com.dpfht.tmdbcleanmvi.feature_movie_details.di.MovieDetailsModule
import com.dpfht.tmdbcleanmvi.framework.base.BaseFragment
import com.dpfht.tmdbcleanmvi.framework.R as frameworkR
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import toothpick.config.Module
import toothpick.ktp.delegate.inject

class MovieDetailsFragment: BaseFragment<MovieDetailsState>() {

  private lateinit var binding: FragmentMovieDetailsBinding
  private val viewModel by inject<MovieDetailsViewModel>()

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

    arguments?.let {
      val movieId = it.getInt("movieId")

      viewModel.setMovieId(movieId)
      lifecycleScope.launch {
        viewModel.intents.send(MovieDetailsIntent.FetchDetails)
      }
    }
  }

  override fun render(state: MovieDetailsState) {
    with(state) {
      showLoading(isLoading)
      showDetails(title, overview, imageUrl)
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.pbLoading.visibility = if (isLoading) {
      View.VISIBLE
    } else {
      View.GONE
    }
  }

  private fun showDetails(title: String, overview: String, imageUrl: String) {
    binding.tvTitleMovie.text = title
    binding.tvDescMovie.text = overview

    if (imageUrl.isNotEmpty()) {
      Picasso.get().load(imageUrl)
        .error(android.R.drawable.ic_menu_close_clear_cancel)
        .placeholder(frameworkR.drawable.loading)
        .into(binding.ivImageMovie)
    }
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
}
