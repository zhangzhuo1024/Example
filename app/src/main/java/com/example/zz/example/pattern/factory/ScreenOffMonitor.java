package com.example.zz.example.pattern.factory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.zz.example.pattern.eventbus.MessageEvent;
import com.example.zz.example.pattern.eventcenter.EventCenter;
import com.example.zz.example.pattern.eventcenter.EventMessage;
import com.example.zz.example.pattern.eventcenter.EventType;

import org.greenrobot.eventbus.EventBus;

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

            //通过消息总线发送消息（内部实现是接口回调）
            EventCenter.getInstance().fireEvnet(EventType.SCREEN_OFF);
//            EventCenter.getInstance().fireEvnetMessage(new EventMessage(EventType.SCREEN_OFF));

            //通过EVENT BUS 发送消息（内部实现是基于观察者模式）
            EventBus.getDefault().post(new MessageEvent(EventType.SCREEN_OFF));
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
