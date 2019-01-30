package com.py.third.viewpager

import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View

class ScaleTransformer : ViewPager.PageTransformer {
    private val MIN_SCALE = 0.70f
    private val MIN_ALPHA = 0.5f

    override fun transformPage(page: View, position: Float) {
        if (position < -1 || position > 1) {
            page.alpha = MIN_ALPHA
            page.scaleX = MIN_SCALE
            page.scaleY = MIN_SCALE

        } else if (position <= 1) { // [-1,1]
            val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))

            if (position < 0) {
                val scaleX = 1 + 0.3f * position;
                Log.d("google_lenve_fb", "transformPage: scaleX:" + scaleX)
                page.scaleX = scaleX
                page.scaleY = scaleX

            } else {
                val scaleX = 1 - 0.3f * position;
                page.scaleX = scaleX
                page.scaleY = scaleX
            }

            page.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
        }
    }
}