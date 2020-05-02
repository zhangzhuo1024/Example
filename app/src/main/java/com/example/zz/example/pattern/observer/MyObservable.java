package com.example.zz.example.pattern.observer;

import java.util.Observable;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/2
 */
public class MyObservable extends Observable {

    public static MyObservable sInstance = new MyObservable();

    private MyObservable() {
    }

    public static MyObservable getInstance() {
        return sInstance;
    }

    public void updateData(String data) {
        setChanged();
        notifyObservers(data);
    }
}
