package com.example.zz.example.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.zz.example.R;

public class PlaneMainActivity extends AppCompatActivity {

    private View mStartGamePlane;
    private View.OnClickListener mStartGameListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PlaneMainActivity.this, PlaneGameActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_main);
        mStartGamePlane = findViewById(R.id.start_game);
        mStartGamePlane.setOnClickListener(mStartGameListener);
    }
}
