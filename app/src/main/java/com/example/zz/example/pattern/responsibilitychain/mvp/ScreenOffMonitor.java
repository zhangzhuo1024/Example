package com.example.zz.example.pattern.responsibilitychain.mvp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/19
 */
public class ScreenOffMonitor implements BaseMonitor {

    private final Context mContext;
    private BroadcastReceiver mScreenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    public ScreenOffMonitor(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        IntentFilter screenOff = new IntentFilter();
        screenOff.addAction(Intent.ACTION_SCREEN_OFF);
        mContext.registerReceiver(mScreenReceiver, screenOff);
    }

    @Override
    public void onDestory() {
        mContext.unregisterReceiver(mScreenReceiver);
    }
}
