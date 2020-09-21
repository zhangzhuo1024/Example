package com.example.zz.example.customcontrol;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.customcontrollib.IconView;
import com.example.zz.example.LogUtils;
import com.example.zz.example.MainActivity;
import com.example.zz.example.R;
import com.example.zz.example.customcontrol.newedittext.NewEditTextActivity;

public class CustomControlActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("onCreate");
        setContentView(R.layout.activity_custom_control);


        Button rect_to_ring_to_rect = findViewById(R.id.rect_to_ring_to_rect);
        Button new_edit_text = findViewById(R.id.new_edit_text);


        rect_to_ring_to_rect.setOnClickListener(v -> {
            Intent intent8 = new Intent(this, RingRectActivity.class);
            startActivity(intent8);
        });

        new_edit_text.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewEditTextActivity.class);
            startActivity(intent);
        });

    }
}
