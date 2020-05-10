package com.example.zz.example.pattern;

import android.app.Activity;
import android.os.Bundle;

import com.example.zz.example.ActivityUtils;
import com.example.zz.example.R;
import com.example.zz.example.pattern.factory.FactoryActivity;
import com.example.zz.example.pattern.observer.ObserverActivity;

import com.example.zz.example.pattern.responsibilitychain.ResponsibilityChainActivity;
import com.example.zz.example.pattern.singleton.SingletonActivity;

public class PatternActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);

        findViewById(R.id.singleton_pattern).setOnClickListener(v -> {
            ActivityUtils.getInstance().goToActivity(PatternActivity.this, SingletonActivity.class);
        });

        findViewById(R.id.observer_pattern).setOnClickListener(v -> {
            ActivityUtils.getInstance().goToActivity(PatternActivity.this, ObserverActivity.class);
        });

        findViewById(R.id.responsibility_chain_pattern).setOnClickListener(v -> {
            ActivityUtils.getInstance().goToActivity(PatternActivity.this, ResponsibilityChainActivity.class);
        });

        findViewById(R.id.factory_pattern).setOnClickListener(v -> {
            ActivityUtils.getInstance().goToActivity(PatternActivity.this, FactoryActivity.class);
        });




    }
}
