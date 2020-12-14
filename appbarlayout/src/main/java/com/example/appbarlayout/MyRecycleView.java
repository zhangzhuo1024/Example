package com.example.appbarlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyRecycleView extends RecyclerView {
    public MyRecycleView(@NonNull Context context) {
        super(context);
    }

    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("eeeeee", "MyRecycleView dispatchTouchEvent ev = " + ev);
        boolean b = super.dispatchTouchEvent(ev);
        Log.e("eeeeee", "MyRecycleView dispatchTouchEvent return = " + b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("eeeeee", "MyRecycleView onInterceptTouchEvent ev = " + ev);
        boolean b = super.onInterceptTouchEvent(ev);
        Log.e("eeeeee", "MyRecycleView onInterceptTouchEvent return = " + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("eeeeee", "MyRecycleView onTouchEvent ev = " + ev);
        boolean b = super.onTouchEvent(ev);
        Log.e("eeeeee", "MyRecycleView onTouchEvent return = " + b);
        return b;
    }
}
