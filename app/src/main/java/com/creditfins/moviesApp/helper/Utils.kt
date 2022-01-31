package com.creditfins.moviesApp.helper

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {
    fun setWindowLayoutForDialog(dialog: Dialog, width: Double, height: Double) {
        val window = dialog.window
        if (window != null) {
            val display = dialog.context.resources.displayMetrics

            val displayWidth = display.widthPixels
            val displayHeight = display.heightPixels

            window.setLayout((displayWidth * width).toInt(), (displayHeight * height).toInt())
            window.setGravity(Gravity.CENTER)
        }
    }

    fun isNotConnected(t: Throwable): Boolean {
        return null == t.message || t.message!!.contains("No address associated with hostname")
                || t.message!!.contains("connect timed out")
                || t.message!!.contains("failed to connect to")
                || t.message!!.contains("timeout")
                || t.message!!.contains("ECONNRESET")
    }

    fun hideSoftInput(view: View?) {
        view?.let { v ->
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

}