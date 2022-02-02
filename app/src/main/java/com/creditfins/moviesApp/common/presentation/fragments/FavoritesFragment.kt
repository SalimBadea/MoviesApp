package com.creditfins.moviesApp.common.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.base.BaseFragment
import com.creditfins.moviesApp.base.OnItemAdapterClickListener
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.presentation.adapters.FavoritesAdapter
import com.creditfins.moviesApp.common.presentation.viewmodel.MoviesListViewModel
import com.creditfins.moviesApp.helper.SharedPreferencesManager
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseFragment(), OnItemAdapterClickListener<Movie> {

    private val mViewModel: MoviesListViewModel by viewModel()
    private lateinit var favoritesAdapter: FavoritesAdapter
    private var mList: MutableList<Movie>? = null
    private var mFavoriteList: MutableList<Movie> = mutableListOf()

    override fun loadLayoutResource(): Int = R.layout.fragment_favorites

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mList = SharedPreferencesManager.getList()

//        mList!!.forEach {
//            if (it.favorite)
//                mFavoriteList.add(it)
//        }

        favoritesRecycleView()

    }

    private fun favoritesRecycleView() {
        val mLayoutManager = GridLayoutManager(this.context, 2)
        mList?.let {list ->
            favoritesAdapter = FavoritesAdapter(list,this)
            rvFavorites?.apply {
                setHasFixedSize(true)
                layoutManager = mLayoutManager
                adapter = favoritesAdapter
                isNestedScrollingEnabled = false
            }
        }
    }

    override fun onItemAdapterClicked(item: Movie) {
        favoritesAdapter.removeMovie(item)
        mList?.let { list ->
            if (list.size == 1)
                favoritesAdapter.clearList()
            SharedPreferencesManager.saveList(list)
        }


    }

}