package com.creditfins.moviesApp.common.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.base.BaseActivity
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.presentation.fragments.MoviesFragment
import com.creditfins.moviesApp.common.presentation.adapters.MoviesAdapter
import com.creditfins.moviesApp.common.presentation.fragments.FavoritesFragment
import com.creditfins.moviesApp.common.presentation.viewmodel.MoviesListViewModel
import com.creditfins.moviesApp.helper.*
import kotlinx.android.synthetic.main.activity_movies_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListActivity : BaseActivity() {

    private val mViewModel: MoviesListViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private var mList: MutableList<Movie> = mutableListOf()
    private var mPage = 1

    private var pageNumber = 1
    private val itemsCount = 10
    private var isLoading = true
    private var pastVisibleItem = 0
    private var visibleItemsCount: Int = 0
    private var totalItemsCount: Int = 0
    private var previousTotal: Int = 0
    private val threshold = 5

    override fun loadLayoutResource() = R.layout.activity_movies_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openFragment(MoviesFragment())

        tvMovies.setOnClickListener {
            tvMovies.background = ContextCompat.getDrawable(this, R.drawable.curved_twenty_blue)
            tvMovies.setTextColor(resources.getColor(R.color.white))
            tvFavorites.background = ContextCompat.getDrawable(this, R.drawable.curved_ten_white)
            tvFavorites.setTextColor(resources.getColor(R.color.black))

           openFragment(MoviesFragment())

        }

        tvFavorites.setOnClickListener {
            tvMovies.background = ContextCompat.getDrawable(this, R.drawable.curved_ten_white)
            tvMovies.setTextColor(resources.getColor(R.color.black))
            tvFavorites.background = ContextCompat.getDrawable(this, R.drawable.curved_twenty_blue)
            tvFavorites.setTextColor(resources.getColor(R.color.white))

           openFragment(FavoritesFragment())

        }
    }

    private fun openFragment(fragment: Fragment){
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.replace(R.id.container, fragment)
        mTransaction.commit()
    }
}