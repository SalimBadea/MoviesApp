package com.creditfins.moviesApp.common.domain.interactor

import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.domain.repository.CommonRepository
import com.creditfins.moviesApp.network.Interactor
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsInteractor(private val mCommonRepository: CommonRepository): Interactor<Int, Flow<Movie>> {
    override fun execute(params: Int): Flow<Movie> = mCommonRepository.getMovieDetails(params)
}