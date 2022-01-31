package com.creditfins.moviesApp.network

data class DataPageResponse<T>(val results: DataResponse<T>, val page: Int = 20)