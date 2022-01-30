package com.creditfins.moviesApp.network

data class DataPageResponse<T>(val data: DataListResponse<T>, val per_page: Int = 20)