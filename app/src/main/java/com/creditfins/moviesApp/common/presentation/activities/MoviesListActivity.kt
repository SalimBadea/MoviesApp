package com.creditfins.moviesApp.common.presentation.activities

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.base.BaseActivity
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.presentation.adapters.MoviesAdapter
import com.creditfins.moviesApp.common.presentation.viewmodel.MoviesListViewModel
import com.creditfins.moviesApp.common.presentation.viewmodel.state.MoviesVS
import com.creditfins.moviesApp.helper.*
import kotlinx.android.synthetic.main.activity_movies_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListActivity : BaseActivity() {

    private val mViewModel: MoviesListViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private var mList: MutableList<Movie> = mutableListOf()
    private var mPage = 1
    private var mIsLoader = false
    private var mIsLastPage = false

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

        if (Utils.isNotConnected(Throwable("No Internet Connection"))) {
            mList = SharedPreferencesManager.getList("Movies")
        } else {
            mViewModel.viewstate.observe(this@MoviesListActivity, {
                when (it) {
                    is MoviesVS.AddMovie -> {
                        Logging.log("Movies List Uploaded")
                        moviesAdapter.AddMovie(it.movie)
                    }

                    is MoviesVS.Empty -> {
                        if (mPage == 1)
                            mList = SharedPreferencesManager.getList("Movies")

                        stopLoader()
                    }
                }
            })
        }

        moviesRecycleView()

        mViewModel.getMoviesList(mPage)

    }

    private fun moviesRecycleView() {
        moviesAdapter = MoviesAdapter()
        val mLayoutManager = GridLayoutManager(this, 2)
        rvMovies?.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = moviesAdapter
            isNestedScrollingEnabled = false

            addOnScrollListener(object :RecyclerView.OnScrollListener(){

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

//            addOnScrollListener(object : PaginationListener(mLayoutManager) {
//                override fun loadMoreItems() {
//                    mIsLoader = true
//                    moviesAdapter.isLoaderVisible(mIsLoader)
//                    mPage++
//                    Logging.log("page>> $mPage")
//
//                }
//
//                override fun isLastPage(): Boolean = mIsLastPage
//
//                override fun isLoading(): Boolean = mIsLoader
//
//            })
        }
    }

    private fun stopLoader() {
        mIsLoader = false
        mIsLastPage = true
        moviesAdapter.isLoaderVisible(mIsLoader)
        moviesAdapter.notifyDataSetChanged()
    }

}