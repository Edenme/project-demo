package com.py.andarch.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 自适应ViewPager的Item高度
 */
public class AutoFitContentHeightViewPager extends ViewPager {
    public static final String POSITION = "position";

    private int mCurrentPosition;
    private int mHeight = 0;
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();


    public AutoFitContentHeightViewPager(Context context) {
        super(context);
    }

    public AutoFitContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                resetHeight(position);
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mChildrenViews.size() > mCurrentPosition) {
            View child = mChildrenViews.get(mCurrentPosition);
            if (child != null) {
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                mHeight = child.getMeasuredHeight();
            }
        }

        if (mHeight != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight(int current) {
        mCurrentPosition = current;
        if (mChildrenViews.size() > current) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mHeight);
            } else {
                layoutParams.height = mHeight;
            }

            setLayoutParams(layoutParams);
        }
    }

    /**
     * 保存View对应的索引,需要自适应高度的一定要设置这个
     */
    public void setViewForPosition(View view, int position) {
        mChildrenViews.put(position, view);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 使用：1、在构造Fragment时保存当前索引
    /**
     *  MayCustomerTrackRecordFragment fragment = new MayCustomerTrackRecordFragment();
     *         Bundle bundle = new Bundle();
     *         bundle.putSerializable(CustomerInfo, item);
     *         bundle.putInt(AutoFitContentHeightViewPager.POSITION, position);
     *         fragment.setArguments(bundle);
     */
    // 2、在Fragment的onCreateView方法中调用：
    /**
     *  if (bundle != null) {
     *         int position = bundle.getInt(AutoFitContentHeightViewPager.POSITION);
     *         if (getActivity() != null) {
     *             ((MayCustomerDetailsActivity) getActivity()).setViewForPosition(view, position);
     *         }
     *     }
     */

    ///////////////////////////////////////////////////////////////////////////
}