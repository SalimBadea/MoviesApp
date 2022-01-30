package com.creditfins.moviesApp.helper

import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.creditfins.moviesApp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Animations {
    /**
     * if you want flash your item use this method
     * when you want finish the flasher use item.clearAnimation();
     *
     * @param item your view example Button or ConstrainLayout or ImageView etc...
     */
    fun flasherItem(item: View) {
        val animation = AlphaAnimation(1f, 0f) // Change alpha from fully visible to invisible
        animation.duration = 500 // duration - half a second
        animation.interpolator = LinearInterpolator() // do not alter animation rate
        animation.repeatCount = 1 // Repeat animation infinitely
        animation.repeatMode =
            Animation.REVERSE // Reverse animation at the end so the button will fade back in
        item.startAnimation(animation)
    }

    /**
     * shake your item before use this method you want create Directory name is anim
     * add in anim file cycle_7.xml
     * <p>
     * <- cycleInterpolator xmlns:android="http://schemas.android.com/apk/res/android"
     * android:cycles="7" ->
     * <p>
     * and add shake.xml
     * <p>
     * <- translate xmlns:android="http://schemas.android.com/apk/res/android"
     * android:fromXDelta="0"
     * android:toXDelta="10"
     * android:duration="1000"
     * android:interpolator="@anim/cycle_7" ->
     *
     * @param context your context from activity
     * @param item    your view example Button or ConstrainLayout or ImageView etc...
     */
    fun shakeItem(context: Context, item: View) {
        val vibration = AnimationUtils.loadAnimation(context, R.anim.shake)
        item.startAnimation(vibration)
    }

    /**
     * used this method when you want scroll recyclerView Horizontal from start to end
     *
     * @param recyclerView your recyclerView
     * @param listSize     size of list
     */
    fun scrollToPositionRecyclerView(recyclerView: RecyclerView, listSize: Int) {
        for (i in 0..listSize) {
            CoroutineScope(IO).launch {
                recyclerView.smoothScrollToPosition(i)
                delay(1000)
            }
            CoroutineScope(IO).launch {
                recyclerView.smoothScrollToPosition(0)
                delay(2500)
            }
        }
    }
}