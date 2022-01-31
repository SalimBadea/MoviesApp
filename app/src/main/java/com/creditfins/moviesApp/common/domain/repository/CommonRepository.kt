package com.creditfins.moviesApp.common.domain.repository

import com.creditfins.moviesApp.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface CommonRepository {

    fun getMoviesList(page: Int): Flow<Movie>
    fun getMovieDetails(id: Int): Flow<Movie>

}