package com.creditfins.moviesApp.common.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.creditfins.moviesApp.BuildConfig
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.base.BaseActivity
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.presentation.viewmodel.MovieDetailsViewModel
import com.creditfins.moviesApp.common.presentation.viewmodel.state.MoviesVS
import com.creditfins.moviesApp.helper.Logging
import com.creditfins.moviesApp.helper.SharedPreferencesManager
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : BaseActivity() {
    override fun loadLayoutResource(): Int = R.layout.activity_movie_details

    private var mFavorites: MutableList<Movie> = mutableListOf()
    private var mFavoritesList : MutableList<Movie> = mutableListOf()
    private val mViewModel: MovieDetailsViewModel by viewModel()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.getIntExtra("id", 0)

        mFavoritesList = SharedPreferencesManager.getList()!!

        mViewModel.viewstate.observe(this@MovieDetailsActivity, {
            when (it) {
                is MoviesVS.GetMovie -> {
                    loadData(it.movie)
                    mFavorites.add(it.movie)
                    if (it.movie.favorite) {
                        ivFavorite.visibility = View.VISIBLE
//                        SharedPreferencesManager.saveList(mFavorites)
                    }
                }

            }
        })

        mViewModel.getMovieDetails(id)
    }

    private fun loadData(movie: Movie) {

        Glide.with(this@MovieDetailsActivity)
            .load(BuildConfig.URL_STORAGE + movie.backdrop_path)
            .into(ivPoster)
        tvName.text = movie.title
        tvDescription.text = movie.overview
        tvReleaseDate.text = movie.release_date
        tvVoteAverage.text = movie.vote_average.toString()
        tvPopularity.text = movie.popularity

        ivAddFavorite.setOnClickListener {
            movie.favorite = true
            ivFavorite.visibility = View.VISIBLE
            ivAddFavorite.visibility = View.INVISIBLE
            mFavoritesList.add(movie)
//            mFavorites.forEach{
//                if (it.favorite)
//                    list.add(it)
//
//            }
            Logging.log(mFavoritesList.size.toString())

            SharedPreferencesManager.saveList(mFavoritesList)

//            mFavorites.add(movie)
//            SharedPreferencesManager.saveList(mFavorites)
            Logging.toast(this@MovieDetailsActivity, "Added to Favorites successfully")
        }


        tvReviews.setOnClickListener {
            startActivity(
                Intent(this@MovieDetailsActivity, ReviewActivity::class.java).putExtra(
                    "id",
                    id
                )
            )
        }
    }

}