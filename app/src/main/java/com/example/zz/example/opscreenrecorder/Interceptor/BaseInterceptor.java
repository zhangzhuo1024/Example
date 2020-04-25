package com.example.zz.example.opscreenrecorder.Interceptor;

import android.app.Activity;
import android.util.Log;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/16
 */
abstract class BaseInterceptor implements Interceptor {

    public Interceptor mNextInterceptor;

    public BaseInterceptor(Interceptor interceptor) {
        this.mNextInterceptor = interceptor;
        Log.e("BaseInterceptor", "BaseInterceptor  mNextInterceptor = interceptor = " + mNextInterceptor);
    }

    @Override
    public void intercept(Activity activity) {
        mNextInterceptor.intercept(activity);
        Log.e("BaseInterceptor", " intercept mNextInterceptor = " + mNextInterceptor);
    }
}
