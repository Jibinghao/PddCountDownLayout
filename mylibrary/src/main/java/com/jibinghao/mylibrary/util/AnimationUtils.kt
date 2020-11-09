package com.jibinghao.mylibrary.util

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.jibinghao.mylibrary.CountDownLayout

/**
 * FileName：AnimationUtils
 * Description：
 * Copyright
 */
object AnimationUtils {
    fun show(view: View) {
        val animation = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 1f, TranslateAnimation.RELATIVE_TO_SELF, 0f
        )
        animation.duration = CountDownLayout.animationDuration
        view.visibility = View.VISIBLE
        view.startAnimation(animation)

    }

    fun hide(view: View) {
        val animation = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 0f,
            TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, -1f
        )
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                view.visibility = View.GONE
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })
        animation.duration = CountDownLayout.animationDuration
        view.startAnimation(animation)
    }
}