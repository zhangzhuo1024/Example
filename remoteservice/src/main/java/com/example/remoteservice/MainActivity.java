package com.example.remoteservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    private RemoteService remoteService;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            remoteService = ((RemoteService) service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private ILocalAidlInterface iLocalAidlInterface;
    private ServiceConnection localconn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iLocalAidlInterface = ILocalAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("111111", " remoteservice MainActivity onCreate");
        setContentView(R.layout.activity_main);
        //RemoteService 服务是启动后给app这个model绑定进行跨进程使用aidl通信的，是不同应用的不同进程间的通信
        intent = new Intent(this, RemoteService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);


        //此处是
        Intent localIntent = new Intent(this, LocalService.class);
        bindService(localIntent, localconn, BIND_AUTO_CREATE);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iLocalAidlInterface.basicTypes(0,0l,true,0f,0d,"从主进程进行点击，把这条信息通过aidl通信传给服务进程");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
