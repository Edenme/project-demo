package com.py.third.viewpager

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RelativeLayout
import com.py.third.R


open class TestViewPagerActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var mAdapter: MyVpAdapter
    private lateinit var mPageLayout: RelativeLayout

    private val mViewPagerTest1: ViewPager by bindView<ViewPager>(R.id.viewpager)
    private val mViewPagerTest2: ViewPager by bindView(R.id.viewpager)


    //使用 val 限制属性不可重新赋值
    private val test = "test"

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_pager_activity)

        mViewPager = findViewById(R.id.viewpager)
        mViewPager.pageMargin = 80
        mViewPager.offscreenPageLimit = 3

        val list = arrayListOf<Int>()
        list.add(R.drawable.icon_001)
        list.add(R.drawable.icon_005)
        list.add(R.drawable.icon_003)
        list.add(R.drawable.icon_006)

        mAdapter = MyVpAdapter(this, list)
        mViewPager.adapter = mAdapter

        mPageLayout = findViewById(R.id.pager_layout)
        mPageLayout.setOnTouchListener(View.OnTouchListener { v, event ->
            return@OnTouchListener mViewPager.dispatchTouchEvent(event)
        })


//        mViewPager.setPageTransformer(false, AlphaTransformer())
        mViewPager.setPageTransformer(false, ScaleTransformer())
    }

    private fun <T : View> Activity.bindView(@IdRes res: Int): Lazy<T> {
        return lazy { findViewById<T>(res) }
    }

}