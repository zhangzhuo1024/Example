package com.example.zz.example.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zz.example.LogUtils;
import com.example.zz.example.R;

public class HandlerActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Handler target = msg.getTarget();
            LogUtils.e("mHandler msg = " + msg + "  target = " + target);
            return false;

        }
    });

    private Handler mHandler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Handler target = msg.getTarget();
            LogUtils.e("mHandler2 msg = " + msg + "  target = " + target);
            return false;

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        Looper.prepare();
        Looper.loop();
        findViewById(R.id.handler_button).setOnClickListener(v->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(11);
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mHandler2.sendEmptyMessageDelayed(22, 3000);
                    mHandler2.sendEmptyMessage(33);
                }
            }).start();
        });

    }
}
