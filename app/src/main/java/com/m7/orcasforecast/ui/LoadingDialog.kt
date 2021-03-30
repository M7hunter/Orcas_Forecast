package com.m7.orcasforecast.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.m7.orcasforecast.R
import com.m7.orcasforecast.databinding.DialogLoadingBinding

class LoadingDialog : DialogFragment() {

    val TAG = "LoadingDialog"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DataBindingUtil.inflate<DialogLoadingBinding>(
            inflater,
            R.layout.dialog_loading,
            container,
            false
        ).apply {
            return root
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        isCancelable = false
    }
}