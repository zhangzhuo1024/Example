package com.example.zz.example.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.zz.example.R;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    private TextView viewById;
    private Timer timer;
    private TimerTask timerTask;
    private int n = 0;
    private String num = "";
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        viewById = (TextView) findViewById(R.id.tv_timer);
        timer(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseTimer();
        handler.removeCallbacksAndMessages(null);
    }

    private void timer(final int delayTime) {
        if (timer != null) {
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(() -> {
                        n++;
                        num = "" + n;
                        viewById.setText(num);
                    });
                }
            };
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(timerTask, delayTime * 1000, 3000);
    }

    private void releaseTimer() {
        if (timer != null) {
            timer.cancel();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
        timerTask = null;
        timer = null;
    }
}