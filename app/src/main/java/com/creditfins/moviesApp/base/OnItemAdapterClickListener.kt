package com.creditfins.moviesApp.base

interface OnItemAdapterClickListener<in T> {
    fun onItemAdapterClicked(item: T)
}