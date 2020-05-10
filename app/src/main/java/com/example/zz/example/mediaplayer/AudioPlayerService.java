package com.example.zz.example.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class AudioPlayerService extends Service {
    private static final String TAG = "AudioPlayerService";
    private IBinder myBinder = new MyBinder();
    private CallBack mCallback;

    public AudioPlayerService() {
    }

    public void setCallBack(CallBack callBack){
        this.mCallback = callBack;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myBinder;
    }

    class MyBinder extends Binder implements Imusic{

        public AudioPlayerService getAudioPlayerService(){
            return AudioPlayerService.this;
        }

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
            mCallback.process(66);//通过mCallback把计算的数据传给activity
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

    interface CallBack{
        void process(int process);
    }
}
