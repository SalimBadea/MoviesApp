package com.creditfins.moviesApp.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creditfins.moviesApp.R


abstract class PaginationAdapter(private val mList: MutableList<*>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_NORMAL = 0
    private val VIEW_TYPE_LOADING = 99
    private var mIsLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LOADING)
            ProgressHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_progress,
                    parent,
                    false
                )
            )
        else
            addCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int = itemCount()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType != VIEW_TYPE_LOADING)
            addBindViewHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (mIsLoaderVisible && position == mList.size - 1)
            VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
    }

    abstract fun addCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun itemCount(): Int

    abstract fun addBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    fun isLoaderVisible(isLoaderVisible: Boolean) {
        mIsLoaderVisible = isLoaderVisible
    }
}

class ProgressHolder(itemView: View) : RecyclerView.ViewHolder(itemView)