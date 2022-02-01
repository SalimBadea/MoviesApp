package com.creditfins.moviesApp.common.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.creditfins.moviesApp.BuildConfig
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.base.BaseActivity
import com.creditfins.moviesApp.common.data.rest.request.AddFavoriteRequest
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

                is MoviesVS.AddFavourite -> {
                    Toast.makeText(this@MovieDetailsActivity, it.message, Toast.LENGTH_LONG).show()
                    ivAddFavorite.visibility = View.INVISIBLE
                    ivFavorite.visibility = View.VISIBLE
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

        ivAddFavorite.setOnClickListener {
            mViewModel.AddFavorite("55dd88cdc0a5ae70c15a07985a188882", AddFavoriteRequest("movie", id, true, "632ff17dcd2fcd771d82db03154a39d412bd59e3"))
        }

        tvReviews.setOnClickListener {
            startActivity(Intent(this@MovieDetailsActivity, ReviewActivity::class.java).putExtra("id", id))
        }
    }
}