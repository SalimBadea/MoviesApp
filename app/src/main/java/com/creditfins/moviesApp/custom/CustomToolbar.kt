package com.creditfins.moviesApp.custom

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.creditfins.moviesApp.R
import kotlinx.android.synthetic.main.custom_toolbar_layout.view.*

class CustomToolbar : ConstraintLayout {
    private lateinit var mTitle: TextView
    private lateinit var mBackButton: ImageButton
    private lateinit var mCloseButton: ImageButton

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
        val v = View.inflate(context, R.layout.custom_toolbar_layout, this)
        val attr = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)
        try {
            val text = attr.getString(R.styleable.CustomToolbar_title)
            val menuVisibility = attr.getBoolean(R.styleable.CustomToolbar_menu_visibility, false)
            val backVisibility = attr.getBoolean(R.styleable.CustomToolbar_back_visibility, false)
            val closeBackVisibility =
                attr.getBoolean(R.styleable.CustomToolbar_close_back_visibility, false)
            val backgroundColor =
                attr.getColor(R.styleable.CustomToolbar_toolbar_background_color, 0)

            mTitle = v.tvTitle
            mBackButton = v.ibnBack
            mCloseButton = v.ibnClose

            mTitle.text = text

            setBackgroundColor(if (backgroundColor == 0) Color.WHITE else backgroundColor)
            mBackButton.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    (context as Activity).finishAfterTransition()
                } else {
                    (context as Activity).finish()
                }
            }

            mCloseButton.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    (context as Activity).finishAfterTransition()
                } else {
                    (context as Activity).finish()
                }
            }

            mBackButton.visibility = if (backVisibility) {
                mCloseButton.visibility = View.VISIBLE
                View.GONE
            } else {
                View.VISIBLE
            }

            if (closeBackVisibility)
                backAndCloseHide()

        } finally {
            attr.recycle()
        }
    }

    fun setTitle(title: String) {
        mTitle.text = title
    }

    fun setTitle(@StringRes title: Int) {
        mTitle.text = context.getString(title)
    }

    fun backButton(): ImageButton = mBackButton

    fun closeButton(): ImageButton = mCloseButton

    private fun backAndCloseHide() {
        mCloseButton.visibility = View.GONE
        mBackButton.visibility = View.GONE
    }
}