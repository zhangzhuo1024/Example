package com.example.zz.example.mediaplayer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

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
    private Imusic myBinder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = ((Imusic) service);
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
        Intent intent = new Intent(ServiceActivity.this, AudioPlayerService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @OnClick({R.id.start, R.id.pause, R.id.resume})
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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
