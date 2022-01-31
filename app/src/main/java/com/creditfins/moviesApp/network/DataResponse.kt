package com.creditfins.moviesApp.network

data class DataResponse<T>(
    val status: String,
    val token: String?,
    val results: MutableList<T>,
    val user: T,
    val page: Int?,
    val message: String
)