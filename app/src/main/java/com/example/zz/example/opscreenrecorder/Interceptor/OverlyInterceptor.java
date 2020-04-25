package com.example.zz.example.opscreenrecorder.Interceptor;

import android.app.Activity;
import android.util.Log;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/16
 */
public class OverlyInterceptor extends BaseInterceptor{

    public OverlyInterceptor(Interceptor interceptor) {
        super(interceptor);
        Log.e("OverlyInterceptor", " interceptor = " + interceptor);
    }

    @Override
    public void intercept(Activity activity) {
        Log.e("OverlyInterceptor", " intercept = ");
        super.intercept(activity);
    }
}
