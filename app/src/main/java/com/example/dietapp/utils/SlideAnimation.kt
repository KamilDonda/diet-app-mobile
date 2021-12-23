package com.example.dietapp.utils

import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.doOnEnd

class SlideAnimation(
    private val maxHeight: Int,
    private val view: View,
    private val animationDuration: Long = 300L
) {

    private var currentProgress = 0

    private val slideUp = ValueAnimator.ofInt(currentProgress, maxHeight).apply {
        duration = animationDuration
        addUpdateListener { animation ->
            currentProgress = (animation.animatedValue as Int)
            view.layoutParams.height = maxHeight - currentProgress
            view.requestLayout()
        }
        doOnEnd {
            view.visibility = View.GONE
        }
    }

    fun start() {
        slideUp.start()
    }

    fun end() {
        slideUp.end()
        view.visibility = View.VISIBLE
    }
}