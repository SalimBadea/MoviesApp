package com.creditfins.moviesApp.network

import com.creditfins.moviesApp.common.domain.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IApiClient {

    @GET("popular")
    suspend fun getPopularMovies(@Query("page") page : Int): DataResponse<Movie>



    @GET("{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movie_id: Int): DataResponse<Movie>
}