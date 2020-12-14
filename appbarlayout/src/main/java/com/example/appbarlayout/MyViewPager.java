package com.example.appbarlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("eeeeee", "MyViewPager dispatchTouchEvent ev = " + ev);
        boolean b = super.dispatchTouchEvent(ev);
        Log.e("eeeeee", "MyViewPager dispatchTouchEvent return = " + b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("eeeeee", "MyViewPager onInterceptTouchEvent ev = " + ev);
        boolean b = super.onInterceptTouchEvent(ev);
        Log.e("eeeeee", "MyViewPager onInterceptTouchEvent return = " + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("eeeeee", "MyViewPager onTouchEvent ev = " + ev);
        boolean b = super.onTouchEvent(ev);
        Log.e("eeeeee", "MyViewPager onTouchEvent return = " + b);
        return b;
    }
}
