package com.example.zz.example.pattern.eventcenter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.zz.example.R;
import com.example.zz.example.pattern.factory.BaseMonitor;
import com.example.zz.example.pattern.factory.MonitorFactory;
import com.example.zz.example.pattern.factory.MonitorType;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/23
 */
public class EventCenterActivity extends Activity implements EventListener {

    private TextView mMessageText;
    private final BaseMonitor mScreenOffMonitor = MonitorFactory.createMonitorByType(this, MonitorType.SCREEN_OFF);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_center);
        mScreenOffMonitor.onCreate();
        EventCenter.getInstance().registerEvent(EventType.SCREEN_OFF, this);

        mMessageText = findViewById(R.id.update_message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScreenOffMonitor.onDestory();
        EventCenter.getInstance().unRegisterEvent(EventType.SCREEN_OFF, this);
    }

    @Override
    public void onEvent(EventType eventType) {
        mMessageText.setText("接收到消息总线发送了的消息，消息为： " + eventType.toString());
    }
}
