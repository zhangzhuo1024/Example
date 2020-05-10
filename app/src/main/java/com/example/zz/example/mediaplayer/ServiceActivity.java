package com.example.zz.example.mediaplayer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.remoteservice.IMyAidlInterface;
import com.example.zz.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends Activity {
    private static final String TAG = "ServiceActivity";
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.pause)
    Button pause;
    @BindView(R.id.resume)
    Button resume;
    @BindView(R.id.remote_service)
    Button remote_service;

    private AudioPlayerService.MyBinder myBinder;
    private AudioPlayerService audioPlayerService;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = ((AudioPlayerService.MyBinder) service);
            //activity中给服务发指令，进行开启，暂停，重启
            myBinder.callStart();
            myBinder.callResume();
            myBinder.callPause();
            audioPlayerService = myBinder.getAudioPlayerService();
            audioPlayerService.setCallBack(new AudioPlayerService.CallBack() {
                @Override
                public void process(int process) {
                    //收到服务返回的数据刷新界面
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private IMyAidlInterface iMyAidlInterface;
    private ServiceConnection remoteConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        //本应用的服务进行连接,完成activity和service间的通信，绑定完成后conn中进行互交
        Intent intent = new Intent();
        intent.setClass(this, AudioPlayerService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);



        //本应用和其他应用的服务进行连接，通过AIDL，实现进程间通信
        //连接时一定要注意：
        // 1、远程的service要在manifest中注册，并且要并且export要设置为true！！！
        // 2、必须要显示调用才能起作用，调用方法如下。或者在manifest中给service设置名称，然后通过setAction在设置setClass 给intent
        // 3、与远程的互交参考上面activity和本应用的互交方式，原理相同

        Intent remoteIntent = new Intent();
            remoteIntent.setClassName("com.example.remoteservice", "com.example.remoteservice.RemoteService");
//        intent.setClass(this, RemoteService.class);  其他应用class 无法直接访问
        bindService(remoteIntent, remoteConn, BIND_AUTO_CREATE);
    }

    @OnClick({R.id.start, R.id.pause, R.id.resume, R.id.remote_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                myBinder.callStart();
                break;
            case R.id.pause:
                myBinder.callPause();
                break;
            case R.id.resume:
                myBinder.callResume();
                break;
            case R.id.remote_service:
                try {
                    iMyAidlInterface.remote("从客户端发送密码001");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
