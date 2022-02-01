package com.creditfins.moviesApp.common.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.common.domain.model.Review
import kotlinx.android.synthetic.main.review_item.view.*

class ReviewsAdapter(private val mList: MutableList<Review> = mutableListOf()) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder>() {


    fun AddReview(review: Review) {
        mList.add(review)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsHolder {
        return ReviewsHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewsHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size


    class ReviewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(review: Review) {
            itemView.tvName.text = review.author
            itemView.tvReview.text = review.content
        }

    }
}