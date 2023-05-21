package com.dpfht.tmdbcleanmvi.feature_genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.tmdbcleanmvi.feature_genre.adapter.GenreAdapter
import com.dpfht.tmdbcleanmvi.feature_genre.databinding.FragmentGenreBinding
import com.dpfht.tmdbcleanmvi.feature_genre.di.GenreModule
import com.dpfht.tmdbcleanmvi.framework.base.BaseFragment
import kotlinx.coroutines.launch
import toothpick.config.Module
import toothpick.ktp.delegate.inject
import javax.inject.Inject

class GenreFragment: BaseFragment<GenreState>() {

  private lateinit var binding: FragmentGenreBinding
  private val viewModel by inject<GenreViewModel>()

  @Inject
  lateinit var adapter: GenreAdapter

  @Inject
  lateinit var loadingDialog: AlertDialog

  override fun getModules(): ArrayList<Module> {
    return arrayListOf(GenreModule(requireContext()))
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentGenreBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    binding.rvGenre.layoutManager = layoutManager
    binding.rvGenre.adapter = adapter

    adapter.onClickGenreListener = object : GenreAdapter.OnClickGenreListener {
      override fun onClickGenre(position: Int) {
        lifecycleScope.launch {
          viewModel.intents.send(GenreIntent.NavigateToNextScreen(position))
        }
      }
    }

    lifecycleScope.launch {
      viewModel.state.collect {
        render(it)
      }
    }

    lifecycleScope.launch {
      viewModel.intents.send(GenreIntent.FetchGenre)
    }
  }

  override fun render(state: GenreState) {
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
