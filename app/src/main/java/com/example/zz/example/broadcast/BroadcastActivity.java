package com.example.zz.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.zz.example.R;

public class BroadcastActivity extends AppCompatActivity {

    private BroadcastReceiver localBroadCasetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String name = intent.getStringExtra("name");
            Toast.makeText(BroadcastActivity.this, "收到本地广播 = " + name, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.localbroadcast");
        intentFilter.addAction("com.example.localbroadcast.filter2");
        LocalBroadcastManager.getInstance(this).registerReceiver(localBroadCasetReceiver, intentFilter);

        findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.localbroadcast");
                intent.putExtra("name", "这是发送的本地广播的信息内容，编号89757");
                LocalBroadcastManager.getInstance(BroadcastActivity.this).sendBroadcast(intent);
            }
        });
        findViewById(R.id.filter2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.localbroadcast.filter2");
                intent.putExtra("name", "这是发送的本地广播的信息内容，action 2");
                LocalBroadcastManager.getInstance(BroadcastActivity.this).sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localBroadCasetReceiver);
    }
}