package com.creditfins.moviesApp.network

import com.creditfins.moviesApp.BuildConfig


class ApiClient {

    companion object {
        val instance: IApiClient = RetrofitApi().getApiClient(BuildConfig.URL_SERVER).create(IApiClient::class.java)
    }
}