package com.example.zz.example.customcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        Button rectToRing = findViewById(R.id.rect_to_ring);
        rectToRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconView.ringChangeToRect(false);
            }
        });

        findViewById(R.id.ring_to_rect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconView.ringChangeToRect(true);
            }
        });

    }
}
