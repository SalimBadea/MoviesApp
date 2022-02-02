package com.creditfins.moviesApp.common.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.base.BaseFragment
import com.creditfins.moviesApp.base.OnItemAdapterClickListener
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.presentation.adapters.MoviesAdapter
import com.creditfins.moviesApp.common.presentation.viewmodel.MoviesListViewModel
import com.creditfins.moviesApp.common.presentation.viewmodel.state.MoviesVS
import com.creditfins.moviesApp.helper.Logging
import com.creditfins.moviesApp.helper.SharedPreferencesManager
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment(), OnItemAdapterClickListener<Movie> {

    private val mViewModel: MoviesListViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private var mList = mutableListOf<Movie>()
    private var mFavoriteList: MutableList<Movie> = mutableListOf()

    private var mPage = 1

    private var pageNumber = 1
    private val itemsCount = 10
    private var isLoading = true
    private var pastVisibleItem = 0
    private var visibleItemsCount: Int = 0
    private var totalItemsCount: Int = 0
    private var previousTotal: Int = 0
    private val threshold = 5
    override fun loadLayoutResource(): Int = R.layout.fragment_movies

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFavoriteList = SharedPreferencesManager.getList()!!

        mViewModel.viewstate.observe(this, {
            when (it) {
                is MoviesVS.AddMovie -> {
                    moviesAdapter.addMovie(it.movie)
                    mList.add(it.movie)
                    Logging.log(mList.size.toString())
                }

            }
        })

        moviesRecycleView()
        mViewModel.getMoviesList(pageNumber)

    }

    private fun moviesRecycleView() {
        moviesAdapter = MoviesAdapter(mList, this)
        val mLayoutManager = GridLayoutManager(this.context, 2)
        rvMovies?.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = moviesAdapter
            isNestedScrollingEnabled = false

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    visibleItemsCount = mLayoutManager.childCount
                    totalItemsCount = mLayoutManager.itemCount
                    pastVisibleItem = mLayoutManager.findFirstVisibleItemPosition()

                    if (dy > 0) {
                        if (isLoading) {
                            if (totalItemsCount > previousTotal) {
                                isLoading = false
                                previousTotal = totalItemsCount

                            }
                        }

                        if (!isLoading && (totalItemsCount - visibleItemsCount) <= (pastVisibleItem + threshold)) {
                            pageNumber++
                            isLoading = true
                            mViewModel.getMoviesList(pageNumber)
                        }
                    }
                }
            })

        }
    }

    private fun stopLoader() {
        isLoading = false
        moviesAdapter.isLoaderVisible(isLoading)
        moviesAdapter.notifyDataSetChanged()
    }

    override fun onItemAdapterClicked(item: Movie) {
        mFavoriteList.add(item)
        SharedPreferencesManager.saveList(mFavoriteList)
    }
}

