package com.creditfins.moviesApp.network


data class DataResponse<T>(
    val results: MutableList<T>,
    val page: Int,
    val id: Int,
    val status_code: Int,
    val status_message: String,
    val success: Boolean,

)