package com.example.zz.example.pattern.singleton;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.zz.example.R;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/4/30
 */
public class SingletonActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton);

        findViewById(R.id.hungry).setOnClickListener(v -> {
            HungrySingle.getsInstance().doSomething();
        });

        findViewById(R.id.lazy).setOnClickListener(v -> {
            LazySingle.getsInstance().doSomething();
        });

        findViewById(R.id.lock_lazy).setOnClickListener(v -> {
            LockLazySingle.getsInstance().doSomething();
        });

        findViewById(R.id.static_singleton).setOnClickListener(v -> {
            StaticSingle.getsInstance().doSomething();
        });

        findViewById(R.id.enum_singleton).setOnClickListener(v -> {
            EnumSingle.INSTANCE.doSomething();
        });
    }
}
