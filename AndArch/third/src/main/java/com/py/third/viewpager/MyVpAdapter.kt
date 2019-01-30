package com.py.third.viewpager;

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

class MyVpAdapter : PagerAdapter {

    private val list: List<Int>
    private val context: Context //可变变量一般要注意使用安全操作符?


    constructor(context: Context, list: List<Int>) {
        this.context = context
        this.list = list
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, p1: Any): Boolean {
        return view == p1
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val iv = ImageView(context)
        iv.setImageResource(list[position])
        container.addView(iv)
        iv.setOnClickListener(View.OnClickListener {
            return@OnClickListener Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
        })

        return iv
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}