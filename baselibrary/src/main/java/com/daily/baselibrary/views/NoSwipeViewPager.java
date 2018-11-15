package com.daily.baselibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.daily.baselibrary.R;


/**
 * description:一个不支持左右滑动的ViewPager
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public class NoSwipeViewPager extends ViewPager {

    boolean isSwipeEnable = false;

    public NoSwipeViewPager(Context context) {
        super(context);
    }

    public NoSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getResources().obtainAttributes(attrs, R.styleable.NoSwipeViewpager);
        isSwipeEnable = a.getBoolean(R.styleable.NoSwipeViewpager_isSwipeEnable, false);
        a.recycle();
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSwipeEnable)
            return super.onTouchEvent(ev);
        else return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
