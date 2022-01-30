package com.creditfins.moviesApp.helper

import android.content.Context

class ResourceProvider(private val mContext: Context) {

    fun getString(resId: Int): String = mContext.getString(resId)

    fun getString(resId: Int, value: String): String = mContext.getString(resId, value)
}