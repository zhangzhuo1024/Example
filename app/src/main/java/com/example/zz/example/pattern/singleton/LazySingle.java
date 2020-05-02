package com.example.zz.example.pattern.singleton;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/30
 */
public class LazySingle {
    private static LazySingle sInstance = null;

    private LazySingle() {

    }

    public static LazySingle getsInstance() {
        if (sInstance == null) {
            sInstance = new LazySingle();
        }
        return sInstance;
    }

    public void doSomething() {

    }
}
