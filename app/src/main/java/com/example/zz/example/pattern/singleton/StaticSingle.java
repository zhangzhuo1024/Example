package com.example.zz.example.pattern.singleton;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/30
 */
public class StaticSingle {
    private StaticSingle() {

    }

    public static StaticSingle getsInstance() {
        return StaticSingleHolder.sInstance;
    }

    public static class StaticSingleHolder {
        public static final StaticSingle sInstance = new StaticSingle();
    }

    public void doSomething() {

    }
}
