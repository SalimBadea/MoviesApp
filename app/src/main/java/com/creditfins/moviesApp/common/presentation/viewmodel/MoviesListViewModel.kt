package com.creditfins.moviesApp.common.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.creditfins.moviesApp.base.BaseViewModel
import com.creditfins.moviesApp.common.domain.interactor.GetMoviesListInteractor
import com.creditfins.moviesApp.common.presentation.viewmodel.state.MoviesVS
import com.creditfins.moviesApp.helper.io
import com.creditfins.moviesApp.helper.ui
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val mGetMoviesListInteractor: GetMoviesListInteractor
) :
    BaseViewModel() {


    val viewstate: LiveData<MoviesVS> get() = mViewState
    private val mViewState = MutableLiveData<MoviesVS>()


    fun getMoviesList(page: Int) {
        viewModelScope.launch {

            try {
                io {
                    mGetMoviesListInteractor.execute(page).collect {
                        ui {
                            mViewState.value = MoviesVS.AddMovie(it)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
