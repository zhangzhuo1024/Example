package com.example.opscreenrecorder;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/16
 */
abstract class BaseIntercept implements Intercept {

    private Intercept mNextIntercept;

    public BaseIntercept(Intercept intercept) {
        this.mNextIntercept = intercept;
    }
    @Override
    public void intercept() {
        mNextIntercept.intercept();
    }
}
