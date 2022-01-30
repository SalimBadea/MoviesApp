package com.creditfins.moviesApp.network

data class DataResponse<T>(
    val status: String,
    val token: String?,
    val data: T,
    val user: T,
    val is_active: Int?,
    val message: String
)