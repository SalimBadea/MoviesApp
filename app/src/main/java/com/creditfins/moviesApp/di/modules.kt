package com.creditfins.moviesApp.di

import com.creditfins.moviesApp.common.data.repository.CommonRepositoryImpl
import com.creditfins.moviesApp.common.data.rest.response.CommonRestDataStore
import com.creditfins.moviesApp.common.domain.interactor.GetMoviesListInteractor
import com.creditfins.moviesApp.common.domain.repository.CommonRepository
import com.creditfins.moviesApp.common.presentation.viewmodel.MoviesListViewModel
import com.creditfins.moviesApp.helper.ResourceProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val commonModule = module {

    viewModel { MoviesListViewModel(get()) }

    single { ResourceProvider(androidApplication()) }

    single<CommonRepository> { CommonRepositoryImpl(get()) }
    single { CommonRestDataStore() }
    single { GetMoviesListInteractor(get()) }
}

val modules = listOf(commonModule)