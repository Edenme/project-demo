package com.py.third

import android.support.v4.view.ViewPager
import android.view.View

//加入”open”，此类才能够被继承，以及重写基类的方法

/**
 * position取值特点：
 * 假设页面从0～1，则：
 * 第一个页面position变化为[0,-1]
 * 第二个页面position变化为[1,0]
 *
 */
class AlphaTransformer : ViewPager.PageTransformer {
    private val MINALPHA = 0.5f

    override fun transformPage(page: View, position: Float) {
        if (position < -1 || position > 1) {
            page.alpha = MINALPHA

        } else {  //不透明->半透明 [0,-1]
            if (position < 0) {
                page.alpha = MINALPHA + (1 + position) * (1 - MINALPHA)

            } else { //半透明->不透明 [1,0]
                page.alpha = MINALPHA + (1 - position) * (1 - MINALPHA)
            }
        }
    }


}