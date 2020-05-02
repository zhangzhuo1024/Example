package com.example.zz.example.pattern.singleton;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/30
 */
public class LockLazySingle {
    private volatile static LockLazySingle sInstance = null;

    private LockLazySingle() {

    }

    public static LockLazySingle getsInstance() {
        if (sInstance == null) {
            synchronized (LockLazySingle.class) {
                if (sInstance == null) {
                    sInstance = new LockLazySingle();
                }
            }
        }
        return sInstance;
    }

    public void doSomething() {

    }
}
