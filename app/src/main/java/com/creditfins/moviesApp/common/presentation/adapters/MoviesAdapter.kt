package com.creditfins.moviesApp.common.presentation.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creditfins.moviesApp.BuildConfig
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.common.domain.model.Movie
import com.creditfins.moviesApp.common.presentation.activities.MovieDetailsActivity
import com.creditfins.moviesApp.helper.PaginationAdapter
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(private val mList: MutableList<Movie> = mutableListOf()) :
    PaginationAdapter(mList) {

    fun AddMovie(movie: Movie) {
        mList.add(movie)
        notifyDataSetChanged()
    }


    override fun addCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MoviesHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )

    override fun itemCount(): Int = mList.size

    override fun addBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MoviesHolder).bind(mList[position])
    }

    inner class MoviesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Movie) {
            itemView.tvName.text = item.title

            Glide.with(itemView.context)
                .load(BuildConfig.URL_STORAGE + item.poster_path)
                .into(itemView.ivPoster)

            itemView.setOnClickListener {
                itemView.context.startActivity(Intent(itemView.context, MovieDetailsActivity::class.java).putExtra("id", item.id))
            }
        }
    }
}