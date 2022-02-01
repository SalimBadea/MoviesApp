package com.creditfins.moviesApp.common.presentation.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.base.BaseActivity
import com.creditfins.moviesApp.common.domain.model.Review
import com.creditfins.moviesApp.common.presentation.adapters.ReviewsAdapter
import com.creditfins.moviesApp.common.presentation.viewmodel.MovieReviewViewModel
import com.creditfins.moviesApp.common.presentation.viewmodel.state.ReviewVS
import com.creditfins.moviesApp.helper.Logging
import kotlinx.android.synthetic.main.activity_movies_list.*
import kotlinx.android.synthetic.main.activity_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewActivity : BaseActivity() {

    private val mViewModel: MovieReviewViewModel by viewModel()
    private lateinit var mAdapter: ReviewsAdapter
    private var mList: MutableList<Review> = mutableListOf()

    private var id: Int = 0
    override fun loadLayoutResource(): Int = R.layout.activity_review

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.getIntExtra("id", 0)

        mViewModel.viewstate.observe(this@ReviewActivity, {
            when(it){
                is ReviewVS.AddReview -> {
                    Logging.log("Reviews List Uploaded")
                    mAdapter.AddReview(it.review)
                }

                is ReviewVS.Empty -> {
                    Logging.toast(this, "There is no reviews yet")
                }
            }
        })

        reviewsRecycleView()
        mViewModel.getReviews(id)
    }

    private fun reviewsRecycleView() {
        mAdapter = ReviewsAdapter()
        rvReviews?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ReviewActivity, RecyclerView.VERTICAL, false)
            adapter = mAdapter
            isNestedScrollingEnabled = false

        }
    }
}