package com.dpfht.tmdbcleanmvi.feature_movie_reviews

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbcleanmvi.feature_movie_reviews.adapter.MovieReviewsAdapter
import com.dpfht.tmdbcleanmvi.feature_movie_reviews.databinding.FragmentMovieReviewsBinding
import com.dpfht.tmdbcleanmvi.feature_movie_reviews.di.MovieReviewsModule
import com.dpfht.tmdbcleanmvi.framework.base.BaseFragment
import kotlinx.coroutines.launch
import toothpick.config.Module
import toothpick.ktp.delegate.inject
import javax.inject.Inject

class MovieReviewsFragment: BaseFragment<FragmentMovieReviewsBinding, MovieReviewsState>(R.layout.fragment_movie_reviews) {

  private val viewModel by inject<MovieReviewsViewModel>()

  @Inject
  lateinit var adapter: MovieReviewsAdapter

  override fun getModules(): ArrayList<Module> {
    return arrayListOf(MovieReviewsModule(requireContext()))
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvReview.layoutManager = layoutManager
    binding.rvReview.adapter = adapter

    binding.rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val xx = recyclerView.computeVerticalScrollRange()
        val xy = recyclerView.computeVerticalScrollOffset()
        val xz = recyclerView.computeVerticalScrollExtent()
        val zz = (xy.toFloat() / (xx - xz).toFloat() * 100).toInt()
        if (zz >= 75 && !viewModel.isLoadingData()) {
          lifecycleScope.launch {
            viewModel.intents.send(MovieReviewsIntent.FetchNextReview)
          }
        }
        super.onScrolled(recyclerView, dx, dy)
      }
    })

    lifecycleScope.launch {
      viewModel.state.collect {
        render(it)
      }
    }

    arguments?.let {
      val movieId = it.getInt("movieId")
      val movieTitle = it.getString("movieTitle")

      binding.tvMovieName.text = movieTitle

      viewModel.setMovieId(movieId)
      lifecycleScope.launch {
        viewModel.intents.send(MovieReviewsIntent.FetchReview)
      }
    }
  }

  override fun render(state: MovieReviewsState) {
    with(state) {
      showLoading(isLoading)
      notifyItemInserted(itemInserted)
    }
  }

  private fun notifyItemInserted(position: Int) {
    if (position > 0) {
      adapter.notifyItemInserted(position)
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.pbLoading.visibility = if (isLoading) {
      View.VISIBLE
    } else {
      View.GONE
    }
  }
}
