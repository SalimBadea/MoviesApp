package com.creditfins.moviesApp.common.data.repository

import com.creditfins.moviesApp.common.data.rest.request.AddFavoriteRequest
import com.creditfins.moviesApp.common.data.rest.response.CommonRestDataStore
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.domain.model.Review
import com.creditfins.moviesApp.common.domain.repository.CommonRepository
import kotlinx.coroutines.flow.Flow

class CommonRepositoryImpl(private val mCommonRestDataStore: CommonRestDataStore) :
    CommonRepository {
    override fun getMoviesList(page: Int): Flow<Movie> = mCommonRestDataStore.getMoviesList(page)

    override fun getMovieDetails(id: Int): Flow<Movie> = mCommonRestDataStore.getMovieDetails(id)

    override fun getMovieReviews(id: Int): Flow<Review> = mCommonRestDataStore.getMovieReview(id)

    override fun addFavorite(addRequest: AddFavoriteRequest): Flow<String> = mCommonRestDataStore.addFavorite(addRequest)
}