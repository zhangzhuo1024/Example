package com.example.zz.example.pattern.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.zz.example.R;
import com.example.zz.example.pattern.eventcenter.EventCenter;
import com.example.zz.example.pattern.eventcenter.EventType;
import com.example.zz.example.pattern.factory.BaseMonitor;
import com.example.zz.example.pattern.factory.MonitorFactory;
import com.example.zz.example.pattern.factory.MonitorType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends AppCompatActivity {

    private TextView mEventBusMessage;
    private final BaseMonitor mScreenOffMonitor = MonitorFactory.createMonitorByType(this, MonitorType.SCREEN_OFF);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        mEventBusMessage = findViewById(R.id.eventbus_message);
        mScreenOffMonitor.onCreate();
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(MessageEvent messageEvent){
        mEventBusMessage.setText("接收到 EVENT BUS 发送了的消息，消息为： " + messageEvent.getMessage().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScreenOffMonitor.onDestory();
    }
}
