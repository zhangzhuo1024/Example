package com.example.zz.example.pattern.factory;

import android.content.Context;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/2
 */
public class MonitorFactory {

    public static BaseMonitor createMonitorByType(Context context, MonitorType monitorType) {
        BaseMonitor monitor = null;
        switch (monitorType) {
            case SCREEN_OFF:
                monitor = new ScreenOffMonitor(context);
                break;
            case PHONE_STATE_CHANGE:
                monitor = new PhoneStateChangeMonitor(context);
                break;
            default:
                break;
        }
        return monitor;
    }

}
