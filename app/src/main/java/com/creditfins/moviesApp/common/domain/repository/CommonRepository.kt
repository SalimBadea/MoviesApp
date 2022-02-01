package com.creditfins.moviesApp.common.domain.repository

import com.creditfins.moviesApp.common.data.rest.request.AddFavoriteRequest
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface CommonRepository {

    fun getMoviesList(page: Int): Flow<Movie>

    fun getMovieDetails(id: Int): Flow<Movie>

    fun getMovieReviews(id:Int): Flow<Review>

    fun addFavorite(api_key: String, addRequest: AddFavoriteRequest): Flow<String>
}