package com.creditfins.moviesApp.common.domain.interactor

import com.creditfins.moviesApp.common.data.rest.request.AddFavoriteRequest
import com.creditfins.moviesApp.common.domain.repository.CommonRepository
import com.creditfins.moviesApp.network.Interactor
import kotlinx.coroutines.flow.Flow

class AddFavoriteInteractor(private val mCommonRepository: CommonRepository): Interactor<AddFavoriteInteractor.Params, Flow<String>> {
    override fun execute(params: Params): Flow<String> = mCommonRepository.addFavorite(params.api_key, params.request)

    data class Params(val api_key: String,val request: AddFavoriteRequest)
}