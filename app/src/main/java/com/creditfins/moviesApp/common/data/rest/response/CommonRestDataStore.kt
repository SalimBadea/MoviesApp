package com.creditfins.moviesApp.common.data.rest.response

import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.helper.SharedPreferencesManager
import com.creditfins.moviesApp.network.ApiClient
import com.creditfins.moviesApp.network.RetrofitApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommonRestDataStore {

    fun getMoviesList(page: Int): Flow<Movie> = flow {
        val response = ApiClient.instance.getPopularMovies(page)
        val list = response.results

        SharedPreferencesManager.savePageCount(page)
        SharedPreferencesManager.saveList("Movies", list)
        list.forEach{
            emit(it)
        }
    }

    fun getMovieDetails(id: Int) : Flow<Movie> = flow{
        val response = ApiClient.instance.getMovieDetails(id)
        emit(response)
    }
}