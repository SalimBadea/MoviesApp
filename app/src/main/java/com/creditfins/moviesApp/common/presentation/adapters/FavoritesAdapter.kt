package com.creditfins.moviesApp.common.presentation.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creditfins.moviesApp.BuildConfig
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.base.OnItemAdapterClickListener
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.presentation.activities.MovieDetailsActivity
import kotlinx.android.synthetic.main.movie_item.view.*

class FavoritesAdapter(
    private val mList: MutableList<Movie> = mutableListOf(),
    private val mCallBack: OnItemAdapterClickListener<Movie>
) :
    RecyclerView.Adapter<FavoritesAdapter.MoviesHolder>() {

    fun removeMovie(movie: Movie) {
        mList.remove(movie)
        notifyDataSetChanged()
    }

    fun clearList() {
        mList.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        return MoviesHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_favorite_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size


    inner class MoviesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Movie) {
            itemView.tvName.text = item.title

            Glide.with(itemView.context)
                .load(BuildConfig.URL_STORAGE + item.poster_path)
                .into(itemView.ivPoster)

            itemView.setOnClickListener {
                itemView.context.startActivity(
                    Intent(
                        itemView.context,
                        MovieDetailsActivity::class.java
                    ).putExtra("id", item.id)
                )
            }

            itemView.ivFavorite.setOnClickListener {
                item.favorite = false
                mCallBack.onItemAdapterClicked(item)
            }
        }
    }

}