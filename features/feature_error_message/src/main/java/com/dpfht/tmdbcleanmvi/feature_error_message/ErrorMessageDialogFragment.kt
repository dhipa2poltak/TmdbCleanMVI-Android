package com.dpfht.tmdbcleanmvi.feature_error_message

import android.os.Bundle
import android.view.View
import com.dpfht.tmdbcleanmvi.feature_error_message.databinding.FragmentErrorMessageDialogBinding
import com.dpfht.tmdbcleanmvi.framework.base.BaseBottomSheetDialogFragment

class ErrorMessageDialogFragment: BaseBottomSheetDialogFragment<FragmentErrorMessageDialogBinding>(R.layout.fragment_error_message_dialog) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    isCancelable = false

    binding.btnOk.setOnClickListener {
      dismiss()
    }

    arguments?.let {
      val message = it.getString("message")

      binding.tvMessage.text = message
    }
  }
}
