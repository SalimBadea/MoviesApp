package com.creditfins.moviesApp.common.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.creditfins.moviesApp.base.BaseViewModel
import com.creditfins.moviesApp.common.domain.interactor.GetMovieReviewsInteractor
import com.creditfins.moviesApp.common.presentation.viewmodel.state.ReviewVS
import com.creditfins.moviesApp.helper.io
import com.creditfins.moviesApp.helper.ui
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieReviewViewModel(private val mGetMovieReviewsInteractor: GetMovieReviewsInteractor): BaseViewModel() {

    val viewstate: LiveData<ReviewVS> get() = mViewState
    val mViewState = MutableLiveData<ReviewVS>()


    fun getReviews(id: Int){
        viewModelScope.launch {
            try {
                io {
                    mGetMovieReviewsInteractor.execute(id).collect {
                        ui {
                            mViewState.value = ReviewVS.AddReview(it)
                        }
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}