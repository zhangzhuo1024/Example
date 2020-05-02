package com.example.zz.example.pattern.observer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.example.zz.example.R;

import java.util.Observable;
import java.util.Observer;


/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/2
 */
public class ObserverActivity extends Activity {

    private Button mObserverButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);
        mObserverButton = findViewById(R.id.observer_button);

        MyObservable.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                mObserverButton.setText((String) arg);
            }
        });

        findViewById(R.id.observer_button).setOnClickListener(v -> {
            MyObservable.getInstance().updateData("观察者刷新收据后的结果");
        });
    }

}
