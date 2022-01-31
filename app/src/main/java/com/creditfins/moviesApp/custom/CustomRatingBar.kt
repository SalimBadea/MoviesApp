package com.creditfins.moviesApp.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.creditfins.moviesApp.R
import kotlinx.android.synthetic.main.custom_rating_bar.view.*

class CustomRatingBar : LinearLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.custom_rating_bar, this)
        val attr = context.obtainStyledAttributes(attrs, R.styleable.CustomRatingBar)
        try {
            val rating = attr.getFloat(R.styleable.CustomRatingBar_star_rating, 0f)
            val size = attr.getInteger(R.styleable.CustomRatingBar_star_size, 0)
            addRating(rating)
            if (size != 0)
                addSize(size)
        } finally {
            attr.recycle()
        }
    }

    fun addRating(rating: Float) {
        removeStars()
        if (rating > 0 && rating <= 0.5) {
            ivStarOne.setImageResource(R.drawable.ic_rating_star_half)
        } else if (rating > 0.5) {
            ivStarOne.setImageResource(R.drawable.ic_rating_star_select)
        }
        if (rating > 1 && rating <= 1.5) {
            ivStarTwo.setImageResource(R.drawable.ic_rating_star_half)
        } else if (rating > 1.5) {
            ivStarTwo.setImageResource(R.drawable.ic_rating_star_select)
        }
        if (rating > 2 && rating <= 2.5) {
            ivStarThree.setImageResource(R.drawable.ic_rating_star_half)
        } else if (rating > 2.5) {
            ivStarThree.setImageResource(R.drawable.ic_rating_star_select)
        }
        if (rating > 3 && rating <= 3.5) {
            ivStarFour.setImageResource(R.drawable.ic_rating_star_half)
        } else if (rating > 3.5) {
            ivStarFour.setImageResource(R.drawable.ic_rating_star_select)
        }
        if (rating > 4 && rating <= 4.5) {
            ivStarFive.setImageResource(R.drawable.ic_rating_star_half)
        } else if (rating > 4.5) {
            ivStarFive.setImageResource(R.drawable.ic_rating_star_select)
        }
    }

    fun addRating(rating: Int) {
        val ratingFloat = rating.toFloat()
        addRating(ratingFloat)
    }

    fun addRating(rating: String) {
        val ratingFloat = rating.toFloat()
        addRating(ratingFloat)
    }

    private fun addSize(size: Int) {
        ivStarOne.layoutParams.width = size
        ivStarOne.layoutParams.height = size
        ivStarTwo.layoutParams.width = size
        ivStarTwo.layoutParams.height = size
        ivStarThree.layoutParams.width = size
        ivStarThree.layoutParams.height = size
        ivStarFour.layoutParams.width = size
        ivStarFour.layoutParams.height = size
        ivStarFive.layoutParams.width = size
        ivStarFive.layoutParams.height = size
    }

    private fun removeStars() {
        ivStarOne.setImageResource(R.drawable.ic_rating_star_unselect)
        ivStarTwo.setImageResource(R.drawable.ic_rating_star_unselect)
        ivStarThree.setImageResource(R.drawable.ic_rating_star_unselect)
        ivStarFour.setImageResource(R.drawable.ic_rating_star_unselect)
        ivStarFive.setImageResource(R.drawable.ic_rating_star_unselect)
    }
}