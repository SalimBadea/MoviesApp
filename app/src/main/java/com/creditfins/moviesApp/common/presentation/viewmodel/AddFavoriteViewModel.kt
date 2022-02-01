package com.creditfins.moviesApp.common.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.creditfins.moviesApp.base.BaseViewModel
import com.creditfins.moviesApp.common.data.rest.request.AddFavoriteRequest
import com.creditfins.moviesApp.common.domain.interactor.AddFavoriteInteractor
import com.creditfins.moviesApp.common.presentation.viewmodel.state.MoviesVS
import com.creditfins.moviesApp.helper.io
import com.creditfins.moviesApp.helper.ui
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddFavoriteViewModel(private val mAddFavoriteInteractor: AddFavoriteInteractor): BaseViewModel() {

    val viewstate: LiveData<MoviesVS> get() = mViewState
    val mViewState = MutableLiveData<MoviesVS>()


//    fun AddFavorite(request: AddFavoriteRequest){
//
//        viewModelScope.launch {
//            try {
//                io {
//                    mAddFavoriteInteractor.execute(request).collect {
//                        ui {
//                            mViewState.value = MoviesVS.AddFavourite(it)
//                        }
//                    }
//                }
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
//    }
}