package com.dpfht.tmdbcleanmvi.framework.common

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.tmdbcleanmvi.framework.R
import javax.inject.Inject
import javax.inject.Provider

class LoadingDialogProvider @Inject constructor(
  val context: Context
): Provider<AlertDialog> {

  override fun get(): AlertDialog {
    return AlertDialog.Builder(context)
      .setCancelable(false)
      .setView(R.layout.dialog_loading)
      .create()
  }
}
