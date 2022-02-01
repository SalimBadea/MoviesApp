package com.creditfins.moviesApp.common.presentation.viewmodel.state

import com.creditfins.moviesApp.common.domain.model.Review

sealed class ReviewVS {

    class AddReview(val review: Review): ReviewVS()
    object Empty: ReviewVS()
}