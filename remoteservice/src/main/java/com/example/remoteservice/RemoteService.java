package com.example.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/10
 */
public class RemoteService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }


    public void callRemote(String aString) {

        Log.e("111111", "服务器收到信息，信息为：" + aString);
    }

    private class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public void remote(String aString) throws RemoteException {
            callRemote(aString);
        }
    }
}
