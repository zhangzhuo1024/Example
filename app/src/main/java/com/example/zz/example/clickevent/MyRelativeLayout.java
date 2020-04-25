package com.example.zz.example.clickevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/24
 */
public class MyRelativeLayout extends RelativeLayout {


    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.i("ClickEventActivity", "MyRelativeLayout dispatchTouchEvent = " + ev);
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                return super.dispatchTouchEvent(ev);
//            case MotionEvent.ACTION_MOVE:
//                return true;
//            case MotionEvent.ACTION_UP:
//                return true;
//        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("ClickEventActivity", "MyRelativeLayout onInterceptTouchEvent = " + ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("ClickEventActivity", "MyRelativeLayout onTouchEvent = " + event);
        return super.onTouchEvent(event);
    }
}
