package com.dpfht.tmdbcleanmvi.framework.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import toothpick.config.Module
import toothpick.ktp.KTP

abstract class BaseFragment<VDB: ViewDataBinding, VS>(
  @LayoutRes protected val contentLayoutId: Int
): Fragment() {

  protected lateinit var binding: VDB

  override fun onAttach(context: Context) {
    super.onAttach(context)

    if (getModules().isNotEmpty()) {
      KTP.openRootScope()
        .openSubScope("APPSCOPE")
        .openSubScope("ActivityScope")
        .openSubScope(this)
        .installModules(*getModules().toTypedArray())
        .inject(this)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)

    return binding.root
  }

  protected abstract fun getModules(): ArrayList<Module>
  protected abstract fun render(state: VS)
}
