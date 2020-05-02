package com.example.zz.example.pattern.responsibilitychain.Interceptor;

import android.app.Activity;
import android.util.Log;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/16
 */
public class PermissionInterceptor extends BaseInterceptor{

    public PermissionInterceptor(Interceptor interceptor) {
        super(interceptor);
        Log.e("PermissionInterceptor", " interceptor = " + interceptor);
    }

    @Override
    public void intercept(Activity activity) {
        Log.e("PermissionInterceptor", " intercept = ");
        super.intercept(activity);
    }
}
