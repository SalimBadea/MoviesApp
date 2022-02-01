package com.creditfins.moviesApp.common.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.creditfins.moviesApp.base.BaseViewModel
import com.creditfins.moviesApp.common.data.rest.request.AddFavoriteRequest
import com.creditfins.moviesApp.common.domain.interactor.AddFavoriteInteractor
import com.creditfins.moviesApp.common.domain.interactor.GetMovieDetailsInteractor
import com.creditfins.moviesApp.common.presentation.viewmodel.state.MoviesVS
import com.creditfins.moviesApp.helper.io
import com.creditfins.moviesApp.helper.ui
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val mGetMovieDetailsInteractor: GetMovieDetailsInteractor,
    private val mAddFavoriteInteractor: AddFavoriteInteractor
) : BaseViewModel() {


    val viewstate: LiveData<MoviesVS> get() = mViewState
    val mViewState = MutableLiveData<MoviesVS>()


    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            try {
                io {
                    mGetMovieDetailsInteractor.execute(id).collect {
                        ui {
                            mViewState.value = MoviesVS.GetMovie(it)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun AddFavorite(api_key: String, request: AddFavoriteRequest) {

        viewModelScope.launch {
            try {
                io {
                    mAddFavoriteInteractor.execute(AddFavoriteInteractor.Params(api_key, request)).collect {
                        ui {
                            mViewState.value = MoviesVS.AddFavourite(it)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}