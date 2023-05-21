package com.dpfht.tmdbcleanmvi.feature_movies_by_genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.adapter.MoviesByGenreAdapter
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.adapter.MoviesByGenreAdapter.OnClickMovieListener
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.databinding.FragmentMoviesByGenreBinding
import com.dpfht.tmdbcleanmvi.feature_movies_by_genre.di.MoviesByGenreModule
import com.dpfht.tmdbcleanmvi.framework.base.BaseFragment
import kotlinx.coroutines.launch
import toothpick.config.Module
import toothpick.ktp.delegate.inject
import javax.inject.Inject

class MoviesByGenreFragment: BaseFragment<MoviesByGenreState>() {

  private lateinit var binding: FragmentMoviesByGenreBinding
  private val viewModel by inject<MoviesByGenreViewModel>()

  @Inject
  lateinit var adapter: MoviesByGenreAdapter

  @Inject
  lateinit var loadingDialog: AlertDialog

  override fun getModules(): ArrayList<Module> {
    return arrayListOf(MoviesByGenreModule(requireContext()))
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMoviesByGenreBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvMoviesByGenre.layoutManager = layoutManager
    binding.rvMoviesByGenre.adapter = adapter

    adapter.onClickMovieListener = object : OnClickMovieListener {
      override fun onClickMovie(position: Int) {
        lifecycleScope.launch {
          viewModel.intents.send(MoviesByGenreIntent.NavigateToNextScreen(position))
        }
      }
    }

    binding.rvMoviesByGenre.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val xx = recyclerView.computeVerticalScrollRange()
        val xy = recyclerView.computeVerticalScrollOffset()
        val xz = recyclerView.computeVerticalScrollExtent()
        val zz = (xy.toFloat() / (xx - xz).toFloat() * 100).toInt()
        if (zz >= 75 && !viewModel.isLoadingData()) {
          lifecycleScope.launch {
            viewModel.intents.send(MoviesByGenreIntent.FetchNextMovie)
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
      val genreId = it.getInt("genreId")
      val genreName = it.getString("genreName")

      val title = "$genreName movies"
      binding.tvTitle.text = title

      viewModel.setGenreId(genreId)
      lifecycleScope.launch {
        viewModel.intents.send(MoviesByGenreIntent.FetchMovie)
      }
    }
  }

  override fun render(state: MoviesByGenreState) {
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
    if (isLoading) {
      loadingDialog.show()
    } else {
      loadingDialog.dismiss()
    }
  }
}
