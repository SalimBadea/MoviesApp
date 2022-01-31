package com.creditfins.moviesApp.common.presentation.viewmodel.state

import com.creditfins.moviesApp.common.domain.model.Movie

sealed class MoviesVS{
    class AddMovie(val movie: Movie) : MoviesVS()
    class AddFavourite(val movie: Movie): MoviesVS()
    object Empty: MoviesVS()
}