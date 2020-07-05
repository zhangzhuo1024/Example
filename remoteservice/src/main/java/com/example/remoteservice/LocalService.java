package com.example.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class LocalService extends Service {
    private IBinder binder = new ILocalAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            localServicePrint(aString);
        }
    };

    private void localServicePrint(String aString) {
        Log.e("111111", "localServicePrint = " + aString);
    }

    public LocalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
