package com.example.zz.example.customcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.zz.example.LogUtils;
import com.example.zz.example.R;

public class CustomControlActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("onCreate");
        setContentView(R.layout.activity_custom_control);
        IconView iconView = findViewById(R.id.start_icon_view);
        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconView.startToMove();
            }
        });

    }
}
