package com.creditfins.moviesApp.common.domain.interactor

import com.creditfins.moviesApp.common.domain.model.Review
import com.creditfins.moviesApp.common.domain.repository.CommonRepository
import com.creditfins.moviesApp.network.Interactor
import kotlinx.coroutines.flow.Flow

class GetMovieReviewsInteractor(private val mCommonRepository: CommonRepository): Interactor<Int, Flow<Review>> {
    override fun execute(params: Int): Flow<Review> = mCommonRepository.getMovieReviews(params)
}