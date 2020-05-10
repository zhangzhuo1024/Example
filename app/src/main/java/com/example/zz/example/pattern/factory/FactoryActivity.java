package com.example.zz.example.pattern.factory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zz.example.R;

import java.util.ArrayList;

public class FactoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);
        initMonitor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerMonitor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterMonitor();
    }

    private void registerMonitor() {
        for (BaseMonitor monitor : monitorArrayList) {
            monitor.onCreate();
        }
    }

    private void unRegisterMonitor() {
        for (BaseMonitor monitor : monitorArrayList) {
            monitor.onDestory();
        }
    }


    private MonitorType[] mMonitorTypes = {MonitorType.SCREEN_OFF, MonitorType.PHONE_STATE_CHANGE};
    private ArrayList<BaseMonitor> monitorArrayList = null;

    private void initMonitor() {
        for (MonitorType mMonitorType : mMonitorTypes) {
            BaseMonitor monitorByType = MonitorFactory.createMonitorByType(this, mMonitorType);
            monitorArrayList.add(monitorByType);
        }
    }
}
