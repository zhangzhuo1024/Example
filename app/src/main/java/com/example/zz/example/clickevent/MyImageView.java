package com.example.zz.example.clickevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zz.example.opscreenrecorder.Interceptor.Interceptor;


/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/24
 */
public class MyImageView extends ImageView {
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("ClickEventActivity", "MyImageView dispatchTouchEvent = " + event);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                return super.dispatchTouchEvent(event);
//            case MotionEvent.ACTION_MOVE:
//                return super.dispatchTouchEvent(event);
//            case MotionEvent.ACTION_UP:
//                return false;
//        }
        return super.dispatchTouchEvent(event);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("ClickEventActivity", "MyImageView onTouchEvent = " + event);
        return super.onTouchEvent(event);

    }

}
