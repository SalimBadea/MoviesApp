package com.creditfins.moviesApp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.helper.Utils

abstract class BaseDialogFragment : DialogFragment() {
    var mWidth = .80
    var mHeight = .30
    abstract fun loadLayoutResource(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(loadLayoutResource(), container, false)
        if (dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawableResource(R.drawable.curved_ten_white)
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return v
    }

    override fun onResume() {
        super.onResume()
        Utils.setWindowLayoutForDialog(dialog!!, mWidth, mHeight)
    }
}