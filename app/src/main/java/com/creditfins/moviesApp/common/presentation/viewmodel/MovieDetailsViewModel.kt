package com.creditfins.moviesApp.common.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.creditfins.moviesApp.base.BaseViewModel
import com.creditfins.moviesApp.common.domain.interactor.GetMovieDetailsInteractor
import com.creditfins.moviesApp.common.presentation.viewmodel.state.MoviesVS
import com.creditfins.moviesApp.helper.io
import com.creditfins.moviesApp.helper.ui
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val mGetMovieDetailsInteractor: GetMovieDetailsInteractor): BaseViewModel() {


    val viewstate : LiveData<MoviesVS> get() = mViewState
    val mViewState = MutableLiveData<MoviesVS>()


    fun getMovieDetails(id: Int){
        viewModelScope.launch {
            try {
                io {
                    mGetMovieDetailsInteractor.execute(id).collect {
                        ui {
                            mViewState.value = MoviesVS.GetMovie(it)
                        }
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}