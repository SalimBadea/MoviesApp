package com.creditfins.moviesApp.custom

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import com.creditfins.moviesApp.R
import kotlinx.android.synthetic.main.progress_layout.view.*

/**
 * Created by µðšţãƒâ ™ on 04/08/2020.
 *  ->
 */
class CustomProgressView : CardView {
    private lateinit var mTvProgress: TextView
    private lateinit var mIvProgress: ImageView
    private lateinit var mPbProgress: ProgressBar
    private lateinit var mBtnRetry: Button

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
        val v = View.inflate(context, R.layout.progress_layout, this)
        val attr = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressView)
        try {
            mTvProgress = v.tvProgress
            mIvProgress = v.ivProgress
            mPbProgress = v.pbProgress
            mBtnRetry = v.btnRetry
            val errorMessage = attr.getString(R.styleable.CustomProgressView_error_message)
            val errorImage = attr.getDrawable(R.styleable.CustomProgressView_progress_image)
            val background =
                attr.getColor(R.styleable.CustomProgressView_progress_background_color, 0)

            mTvProgress.text = errorMessage
            mIvProgress.setImageDrawable(errorImage)
            setCardBackgroundColor(background)
        } finally {
            attr.recycle()
        }
    }

    fun showProgressView() {
        show()
        setCardBackgroundColor(Color.BLACK)
        alpha = 0.5f
        (context as Activity).window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun hideProgressView() {
        setCardBackgroundColor(Color.WHITE)
        alpha = 1f
        hide()
        (context as Activity).window.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun show() {
        visibility = View.VISIBLE
    }

    fun hide() {
        visibility = View.GONE
    }

    fun showProgress() {
        mPbProgress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        mPbProgress.visibility = View.GONE
    }

    fun showRetryButton() {
        mBtnRetry.visibility = View.VISIBLE
    }

    fun hideRetryButton() {
        mBtnRetry.visibility = View.GONE
    }

    fun showImage() {
        mIvProgress.visibility = View.VISIBLE
    }

    fun hideImage() {
        mIvProgress.visibility = View.GONE
    }

    fun addImageResource(@DrawableRes image: Int) {
        mIvProgress.setImageResource(image)
    }

    fun showMessageError(errorMessage: String) {
        showMessageView()
        mTvProgress.text = errorMessage
    }

    fun showMessageError(@StringRes errorMessage: Int) {
        showMessageView()
        mTvProgress.text = context.getString(errorMessage)
    }

    fun showMessageErrorWithButton(errorMessage: String, buttonText: String, action: () -> Unit) {
        hideProgress()
        mTvProgress.text = errorMessage
        showRetryButton()
        mBtnRetry.text = buttonText
        mBtnRetry.setOnClickListener {
            action()
            hideRetryButton()
        }
    }

    fun noInternetFound(action: () -> Unit) {
        showInternetData()
        mBtnRetry.setOnClickListener {
            action()
            hideInternetData()
        }
    }

    fun showUnauthenticated(action: () -> Unit) {
        hideProgress()
        showRetryButton()
        mBtnRetry.text = context.getString(R.string.sign_in)
        mTvProgress.visibility = View.VISIBLE
        showImage()
        mTvProgress.text = context.getString(R.string.unauthenticated)
        mIvProgress.setImageResource(R.drawable.ic_sign_in)
        mBtnRetry.setOnClickListener {
            action()
            hideInternetData()
        }
    }

    private fun showInternetData() {
        hideProgress()
        showRetryButton()
        mBtnRetry.text = context.getString(R.string.retry)
        mTvProgress.visibility = View.VISIBLE
        showImage()
        mTvProgress.text = context.getString(R.string.no_internet)
        mIvProgress.setImageResource(R.drawable.disconnected)
    }

    private fun hideInternetData() {
        showProgress()
        hideRetryButton()
        mTvProgress.visibility = View.GONE
        hideImage()
    }

    private fun showMessageView() {
        mTvProgress.visibility = View.VISIBLE
        hideProgress()
        hideRetryButton()
        hideImage()
    }

    fun hideMessageView() {
        showProgress()
        hideRetryButton()
        mTvProgress.visibility = View.GONE
        hideImage()
    }

    fun isShowed(): Boolean = visibility != View.GONE
}