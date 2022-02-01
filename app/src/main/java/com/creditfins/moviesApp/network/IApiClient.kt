package com.creditfins.moviesApp.network

import com.creditfins.moviesApp.common.data.rest.request.AddFavoriteRequest
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.domain.model.Review
import retrofit2.http.*

interface IApiClient {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): DataResponse<Movie>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movie_id: Int): Movie

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(@Path("movie_id") movie_id: Int): DataResponse<Review>

    @POST("account/8350085/favorite")
    suspend fun addtoFavorite(@Query("api_key") api_key: String, @Body favoriteRequest: AddFavoriteRequest): DataResponse<String>
}