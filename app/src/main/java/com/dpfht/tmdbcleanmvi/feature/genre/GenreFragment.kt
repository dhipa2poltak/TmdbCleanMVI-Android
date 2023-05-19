package com.dpfht.tmdbcleanmvi.feature.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.tmdbcleanmvi.databinding.FragmentGenreBinding
import com.dpfht.tmdbcleanmvi.feature.genre.GenreState.Idle
import com.dpfht.tmdbcleanmvi.feature.genre.GenreState.IsLoading
import com.dpfht.tmdbcleanmvi.feature.genre.GenreState.NotifyItemInserted
import com.dpfht.tmdbcleanmvi.feature.genre.adapter.GenreAdapter
import com.dpfht.tmdbcleanmvi.feature.genre.di.GenreModule
import kotlinx.coroutines.launch
import toothpick.config.Module
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import javax.inject.Inject

class GenreFragment: Fragment() {

  private lateinit var binding: FragmentGenreBinding
  private val viewModel by inject<GenreViewModel>()

  @Inject
  lateinit var adapter: GenreAdapter

  @Inject
  lateinit var loadingDialog: AlertDialog

  fun getModules(): ArrayList<Module> {
    return arrayListOf(GenreModule(requireContext()))
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    KTP.openRootScope()
      .openSubScope("APPSCOPE")
      .openSubScope("ActivityScope")
      .openSubScope(this)
      .installModules(*getModules().toTypedArray())
      .inject(this)

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

    /*
    binding.rvGenre.adapter = adapter

    adapter.onClickGenreListener = object : GenreAdapter.OnClickGenreListener {
      override fun onClickGenre(position: Int) {
        lifecycleScope.launch {
          viewModel.intents.send(GenreIntent.NavigateToNextScreen(position))
        }
      }
    }
    */

    /*
    lifecycleScope.launch {
      viewModel.state.collect {
        render(it)
      }
    }

    lifecycleScope.launch {
      viewModel.intents.send(GenreIntent.FetchGenre)
    }
    */
  }

  fun render(state: GenreState) {
    when (state) {
      is NotifyItemInserted -> {
        doNotifyItemInserted(state.value)
      }
      is IsLoading -> {
        showLoading(state.value)
      }
      is Idle -> {
      }
    }
  }

  private fun doNotifyItemInserted(position: Int) {
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

  override fun onPause() {
    super.onPause()
    lifecycleScope.launch {
      viewModel.intents.send(GenreIntent.EnterIdleState)
    }
  }
}
