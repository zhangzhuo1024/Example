package com.example.zz.example.pattern.singleton;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/30
 */
public class HungrySingle {
    private static HungrySingle sInstance = new HungrySingle();

    private HungrySingle() {
    }

    public static HungrySingle getsInstance() {
        return sInstance;
    }

    public void doSomething() {

    }
}
