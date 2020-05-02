package com.example.zz.example;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/2
 */
public class ActivityUtils {

    private static ActivityUtils sInstance = new ActivityUtils();

    private ActivityUtils() {
    }

    public static ActivityUtils getInstance() {
        return sInstance;
    }

    public void goToActivity(Activity currentActivity, Class<? extends Activity> nextActivity) {
        Intent intent = new Intent();
        //此处实现的方法序列化对象intent.putExtra(name, serializable);
        intent.setClass(currentActivity, nextActivity);
        currentActivity.startActivity(intent);
    }

    public void goToActivity(Activity currentActivity, Class<? extends Activity> nextActivity, ActivityParam param) {

        Intent intent = new Intent();
        intent.putExtra("activityParams", param);
        intent.setClass(currentActivity, nextActivity);
        currentActivity.startActivity(intent);
    }

    private class ActivityParam implements Serializable {
        //在这个类中可以写想要传递的任意参数，
        //包括，对象，数组，string，集合框架等等
    }
}
