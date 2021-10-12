package com.example.zz.example.pattern.singleton;

import android.content.Context;

import java.io.File;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/30
 */
public class LockLazySingle {
    private volatile static LockLazySingle sInstance = null;
    private static Context mContext;

    private LockLazySingle(Context context) {
        mContext = context;
    }

    public static LockLazySingle getsInstance(SingletonActivity singletonActivity) {

        if (sInstance == null) {
            synchronized (LockLazySingle.class) {
                if (sInstance == null) {
                    sInstance = new LockLazySingle(singletonActivity.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public void doSomething() {
        File sys = mContext.getExternalFilesDir("sys");
    }
}
