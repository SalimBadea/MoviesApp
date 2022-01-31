package com.creditfins.moviesApp.network

data class DataResponse<T>(
    val results: MutableList<T>,
    val page: Int?,
)