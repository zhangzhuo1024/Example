package com.example.zz.example.clickspanner;

import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/3/6
 */
public class weiboclickspan extends ClickableSpan {
    private WeiboContentTestActivity.onTextViewClickListener mListener;

    @Override
    public void onClick(View arg0) {
        mListener.clickTextView();
    }
    public weiboclickspan(WeiboContentTestActivity.onTextViewClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        mListener.setStyle(ds);
    }
}
