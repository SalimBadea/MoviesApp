package com.creditfins.moviesApp.network

import com.creditfins.moviesApp.BuildConfig
import com.creditfins.moviesApp.helper.SharedPreferencesManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class RetrofitApi {

    companion object {
        val instance: IApiClient =
            RetrofitApi().getApiClient(BuildConfig.URL_SERVER).create(IApiClient::class.java)
        const val CONNECTION_TIMEOUT: Long = 180L
        const val READ_TIMEOUT: Long = 180L
        const val WRITE_TIMEOUT: Long = 180L
    }

    open fun getApiClient(url: String): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor {
            val original = it.request()
            val request = original.newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NWRkODhjZGMwYTVhZTcwYzE1YTA3OTg1YTE4ODg4MiIsInN1YiI6IjVjOWEyYTgwOTI1MTQxMTA2N2Y2MzQ4YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.hzOC1AMhJll8qZcJ-pumQYNQyo4IM3hQXH7J62HF1Tc")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .method(original.method, original.body)
                .build()
            it.proceed(request)
        }

        val client = httpClient
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        val retrofitBuilder = Retrofit.Builder().apply {
            baseUrl(url)
            client(client)
            addConverterFactory(GsonConverterFactory.create())
        }

        return retrofitBuilder.build()
    }
}