package com.creditfins.moviesApp.common.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.creditfins.moviesApp.BuildConfig
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.base.BaseActivity
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.presentation.viewmodel.MovieDetailsViewModel
import com.creditfins.moviesApp.common.presentation.viewmodel.state.MoviesVS
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : BaseActivity() {
    override fun loadLayoutResource(): Int = R.layout.activity_movie_details

    private val mViewModel: MovieDetailsViewModel by viewModel()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.getIntExtra("id", 0)
        mViewModel.viewstate.observe(this@MovieDetailsActivity,{
            when(it){
                is MoviesVS.GetMovie -> {
                    loadData(it.movie)
                }
            }
        })

        mViewModel.getMovieDetails(id)
    }

    private fun loadData(movie: Movie) {

        Glide.with(this@MovieDetailsActivity)
            .load(BuildConfig.URL_STORAGE+ movie.backdrop_path)
            .into(ivPoster)
        tvName.text = movie.title
        tvDescription.text = movie.overview
        tvReleaseDate.text = movie.release_date
        tvVoteAverage.text = movie.vote_average.toString()
        tvPopularity.text = movie.popularity
    }
}