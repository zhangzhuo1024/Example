package com.example.zz.example.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class AudioPlayerService extends Service {
    private static final String TAG = "AudioPlayerService";
    private IBinder myBinder = new MyBinder();

    public AudioPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myBinder;
    }

    class MyBinder extends Binder implements Imusic{

        @Override
        public void callStart() {
            start();
        }

        @Override
        public void callPause() {
            pause();
        }

        @Override
        public void callResume() {
            resume();
        }
    }
    public void start(){
        Log.i(TAG, "start play music");
    }
    public void pause(){
        Log.i(TAG, "pause music");
    }
    public void resume(){
        Log.i(TAG, "resume music");
    }
}
