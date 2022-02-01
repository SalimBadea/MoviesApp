package com.creditfins.moviesApp.common.data.rest.response

import com.creditfins.moviesApp.common.data.rest.request.AddFavoriteRequest
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.domain.model.Review
import com.creditfins.moviesApp.helper.SharedPreferencesManager
import com.creditfins.moviesApp.network.ApiClient
import com.creditfins.moviesApp.network.RetrofitApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommonRestDataStore {

    fun getMoviesList(page: Int): Flow<Movie> = flow {
        val response = ApiClient.instance.getPopularMovies(page)
        val list = response.results

        list.forEach { emit(it) }
    }

    fun getMovieDetails(id: Int): Flow<Movie> = flow {
        val response = ApiClient.instance.getMovieDetails(id)
        emit(response)
    }

    fun getMovieReview(id: Int): Flow<Review> = flow {
        val response = ApiClient.instance.getMovieReviews(id)
        val list = response.results
        list.forEach { emit(it) }
    }

    fun addFavorite(api_key: String, addRequest: AddFavoriteRequest): Flow<String> = flow {
        emit(ApiClient.instance.addtoFavorite(api_key,addRequest).status_message)
    }
}