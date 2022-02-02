package com.creditfins.moviesApp.common.presentation.viewmodel.state

import com.creditfins.moviesApp.common.domain.model.Movie

sealed class MoviesVS{
    class AddMovie(val movie: Movie) : MoviesVS()
    class GetMovie(val movie: Movie) : MoviesVS()
    class AddFavourite(val favourite: MutableList<Movie>): MoviesVS()
    object Empty: MoviesVS()
}
