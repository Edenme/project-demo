package com.py.third.viewpager;

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View

open class MyVpAdapter : PagerAdapter {


    private var list: List<Int>? = null
    private var context: Context? = null //可变变量一般要注意使用安全操作符?


    constructor(context: Context, list: List<Int>) {
        this.context = context
        this.list = list
    }

//    public MyVpAdapter(Context context, List<Integer> list)
//    {
//        this.context = context;
//        this.list = list;
//    }
//
//    @Override
//    public int getCount()
//    {
//        return list.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object
//    object) {
//        return view ==
//        object;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, final int position)
//    {
//        ImageView iv = new ImageView(context);
//        iv.setImageResource(list.get(position));
//        container.addView(iv);
//
//        iv.setOnClickListener(new View . OnClickListener () {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        return iv;
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object
//    object) {
//        container.removeView((View)
//        object);
//    }

    override fun getCount(): Int {
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {

    }

}